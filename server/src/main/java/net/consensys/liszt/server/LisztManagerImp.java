package net.consensys.liszt.server;

import java.util.*;
import net.consensys.liszt.accountmanager.*;
import net.consensys.liszt.blockchainmanager.*;
import net.consensys.liszt.blockchainmanager.contract.TransferDone;
import net.consensys.liszt.core.common.Batch;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.Proof;
import net.consensys.liszt.core.crypto.PublicKey;
import net.consensys.liszt.provermanager.ProverListener;
import net.consensys.liszt.provermanager.ProverService;
import net.consensys.liszt.provermanager.ProverServiceImp;
import net.consensys.liszt.transfermanager.*;
import org.springframework.stereotype.Service;

@Service
public class LisztManagerImp implements LisztManager, ProverListener {

  private final TransferService transferService;
  private final AccountService accountService;
  private final BatchService batchService;
  private final ProverService proveService;
  private final BlockchainService blockchainService;
  private Hash lastRootHash;
  private final short rollupId;
  private final short otherRollupId;

  public LisztManagerImp(short rollupId, short otherRollupId) {
    this.rollupId = rollupId;
    this.otherRollupId = otherRollupId;
    AccountStateProvider accountsStateProvider = new InMemoryAccountsStateProvider();
    Map<Hash, AccountsState> accountsState = accountsStateProvider.initialAccountsState();
    this.lastRootHash = accountsStateProvider.lastAcceptedRootHash();
    transferService = new TransferServiceImpl(accountsStateProvider.batchSize());
    accountService =
        new AccountServiceImp(
            new AccountRepositoryImp(accountsState), accountsStateProvider.lastAcceptedRootHash());
    batchService = new BatchServiceImpl();
    proveService = new ProverServiceImp();
    blockchainService = new BlockchainServiceImp();
    try {
      blockchainService.startLocalNode();
      // blockchainService.deployContract();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
    proveService.registerListener(this);
  }

  @Override
  public synchronized boolean addTransfer(RTransfer rtx) {

    if (!accountService.checkBasicValidity(rtx, this.lastRootHash)) {
      return false;
    }

    // if acc from locked
    List<Account> lockedAccounts = accountService.getLockAccounts(this.lastRootHash);

    Optional<Account> lockedAccount =
        lockedAccounts.stream().filter(a -> a.publicKey.equals(rtx.from)).findAny();

    if (lockedAccount.isPresent()) {
      TransferDone transferDone = null;
      try {
        transferDone =
            blockchainService.getTransferDone(
                otherRollupId, new Hash(rtx.hashOfThePendingTransfer.get()));
        if (!(rtx.to.hash.asHex.equals(transferDone.from)
            && rtx.amount.equals(transferDone.amount))) {
          return false;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

      if (transferDone == null) {
        return false;
      }
    }

    transferService.addTransfer(rtx);

    List<RTransfer> transfers;
    List<RTransfer> invalidTransfers = new ArrayList<>();
    do {
      transfers = transferService.selectRTransfersForNextBatch(this.lastRootHash, invalidTransfers);
      if (transfers.isEmpty()) {
        return true;
      }

      invalidTransfers = accountService.updateIfAllTransfersValid(transfers, this.lastRootHash);
    } while (!invalidTransfers.isEmpty());

    Hash newRootHash = accountService.getLastAcceptedRootHash();
    batchService.startNewBatch(lastRootHash, newRootHash, transfers);
    Batch batch = batchService.getBatchToProve();

    this.lastRootHash = newRootHash;
    proveService.proveBatch(batch);
    return true;
  }

  @Override
  public synchronized void onNewProof(Proof proof) {
    Batch batch = batchService.getBatch(proof.rootHash);
    try {
      blockchainService.submit(batch, proof);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public synchronized void onChainReorg(Hash rootHash) {
    this.lastRootHash = rootHash;
  }

  @Override
  public synchronized void onBatchIncluded(Batch batch, int blockHight, Hash blockHash) {
    batchService.updateBatchStatus(batch, blockHight, blockHash);
  }

  @Override
  public synchronized RTransferState getRTransferStatus(RTransfer transfer) {
    List<BatchStatus> batchStates = batchService.getBatchesForTransfer(transfer);
    RTransferState rTransferState = new RTransferState(transfer, batchStates);
    return rTransferState;
  }

  @Override
  public synchronized Account getAccount(PublicKey owner) {
    return accountService.getAccount(accountService.getLastAcceptedRootHash(), owner.hash);
  }

  @Override
  public synchronized Account getAccount(String owner) {
    return accountService.getAccount(accountService.getLastAcceptedRootHash(), new Hash(owner));
  }

  @Override
  public synchronized List<Account> getLockAccounts() {
    return accountService.getLockAccounts(accountService.getLastAcceptedRootHash());
  }

  @Override
  public synchronized long getLockDoneTimeout(Hash txHash) throws Exception {
    return blockchainService.getLockedDone(rollupId, txHash);
  }

  @Override
  public synchronized TransferDone getTransferDone(short rollupId, Hash hash) throws Exception {
    return blockchainService.getTransferDone(rollupId, hash);
  }
}

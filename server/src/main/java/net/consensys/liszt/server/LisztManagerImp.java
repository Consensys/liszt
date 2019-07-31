package net.consensys.liszt.server;

import java.util.*;
import net.consensys.liszt.accountmanager.Account;
import net.consensys.liszt.accountmanager.AccountService;
import net.consensys.liszt.accountmanager.AccountServiceImp;
import net.consensys.liszt.accountmanager.Accounts;
import net.consensys.liszt.blockchainmanager.*;
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

  public LisztManagerImp() {
    InitialConfiguration initConf = new InitialConfiguration();
    transferService = new TransferServiceImpl(initConf.batchSize);
    accountService = new AccountServiceImp(initConf.accountState, initConf.initialRootHash);
    batchService = new BatchServiceImpl();
    proveService = new ProverServiceImp();
    blockchainService = new BlockchainServiceImp();
    this.lastRootHash = initConf.initialRootHash;
    proveService.registerListener(this);
  }

  @Override
  public synchronized boolean addTransfer(RTransfer rtx) {
    if (!accountService.checkBasicValidity(rtx, this.lastRootHash)) {
      return false;
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
    blockchainService.submit(batch, proof);
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

  public synchronized Account getAccount(PublicKey owner) {
    return accountService.getAccount(owner, accountService.getLastAcceptedRootHash());
  }

  private LinkedHashMap<PublicKey, Account> createAccounts() {
    PublicKey alice = new PublicKey("Alice");
    PublicKey bob = new PublicKey("Bob");
    PublicKey zac = new PublicKey("Zac");
    List<PublicKey> publicKeys = Arrays.asList(new PublicKey[] {alice, bob, zac});
    LinkedHashMap<PublicKey, Account> accounts = Accounts.accounts(publicKeys);
    return accounts;
  }
}

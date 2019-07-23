package net.consensys.liszt.server;

import java.util.List;
import net.consensys.liszt.accountmanager.AccountService;
import net.consensys.liszt.blockchainmanager.*;
import net.consensys.liszt.core.common.Batch;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.Proof;
import net.consensys.liszt.provermanager.ProverService;
import net.consensys.liszt.transfermanager.BatchService;
import net.consensys.liszt.transfermanager.BatchState;
import net.consensys.liszt.transfermanager.RTransferState;
import net.consensys.liszt.transfermanager.TransferService;

public class ControllerImp implements Controller {

  private final TransferService transferService;
  private final AccountService accountService;
  private final BatchService batchService;
  private final ProverService proveService;
  private final BlockchainService blockchainService;

  private Hash lastRootHash;

  public ControllerImp(
      TransferService transferService,
      AccountService accountService,
      BatchService batchService,
      ProverService proveService,
      BlockchainService blockchainService) {
    this.transferService = transferService;
    this.accountService = accountService;
    this.batchService = batchService;
    this.proveService = proveService;
    this.blockchainService = blockchainService;
    lastRootHash = new Hash();
  }

  @Override
  public boolean addTransfer(RTransfer rtx) {
    if (!accountService.checkBasicValidity(rtx, this.lastRootHash)) {
      return false;
    }

    transferService.addTransfer(rtx);

    List<RTransfer> transfers;
    List<RTransfer> invalidTransfers;
    do {
      transfers = transferService.selectRTransfersForNextBatch(this.lastRootHash);
      if (!transfers.isEmpty()) {
        break;
      }
      invalidTransfers = accountService.updateIfAllTransfersValid(transfers, this.lastRootHash);
    } while (!transfers.isEmpty() && invalidTransfers.isEmpty());

    batchService.startNewBatch(this.lastRootHash);
    lastRootHash = accountService.getLastAcceptedRootHash();
    batchService.addToBatch(transfers, lastRootHash);
    Batch batch = batchService.getBatchToProve();
    proveService.proveBatch(batch);
    return true;
  }

  @Override
  public void onNewProof(Proof proof) {
    Batch batch = batchService.getBatch(proof.rootHash);
    blockchainService.submit(batch, proof);
  }

  @Override
  public void onChainReorg(Hash rootHash) {
    this.lastRootHash = rootHash;
  }

  @Override
  public void onBatchIncluded(Batch batch, int blockHight, Hash blockHash) {
    batchService.updateBatchStatus(batch, blockHight, blockHash);
  }

  @Override
  public RTransferState getRTransferStatus(Hash transferHash) {
    List<BatchState> batchStates = batchService.getBatchesForTransfer(transferHash);
    RTransferState rTransferState = new RTransferState(transferHash, batchStates);
    return rTransferState;
  }
}

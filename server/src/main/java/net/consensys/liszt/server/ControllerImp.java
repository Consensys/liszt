package net.consensys.liszt.server;

import java.util.List;
import net.consensys.liszt.accountmanager.AccountService;
import net.consensys.liszt.blockchainmanager.*;
import net.consensys.liszt.core.common.Batch;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Proof;
import net.consensys.liszt.provemanager.ProveService;
import net.consensys.liszt.transfermanager.BatchService;
import net.consensys.liszt.transfermanager.BatchState;
import net.consensys.liszt.transfermanager.RTransferState;
import net.consensys.liszt.transfermanager.TransferService;

public class ControllerImp implements Controller {

  private final TransferService transferService;
  private final AccountService accountService;
  private final BatchService batchService;
  private final ProveService proveService;
  private final BlockchainService blockchainService;

  private byte[] lastRootHash;

  public ControllerImp(
      TransferService transferService,
      AccountService accountService,
      BatchService batchService,
      ProveService proveService,
      BlockchainService blockchainService) {
    this.transferService = transferService;
    this.accountService = accountService;
    this.batchService = batchService;
    this.proveService = proveService;
    this.blockchainService = blockchainService;
  }

  @Override
  public boolean addTransfer(RTransfer rtx) {

    if (!accountService.checkBasicValidity(rtx)) {
      // TODO update transfers status
      return false;
    }

    transferService.addTransfer(rtx);
    List<RTransfer> transfers = transferService.selectRTransfersForNextBatch(this.lastRootHash);
    if (!transfers.isEmpty()) {
      handleNewBatch(transfers);
    }
    // TODO update transfers status
    return true;
  }

  @Override
  public void onNewProof(Proof proof) {
    // TODO update transfers status
    Batch batch = batchService.getBatch(proof.rootHash);
    blockchainService.submit(batch, proof);
  }

  @Override
  public void onChainReorg(byte[] rootHash) {
    this.lastRootHash = rootHash;
  }

  @Override
  public void onBatchIncluded(Batch batch, int blockHight, byte[] blockHash) {
    batchService.updateBatchStatus(batch, blockHight, blockHash);
  }

  @Override
  public RTransferState getRTransferStatus(byte[] transferHash) {
    List<BatchState> batchStates = batchService.getBatchesForTransfer(transferHash);
    RTransferState rTransferState = new RTransferState(transferHash, batchStates);
    return rTransferState;
  }

  private void handleNewBatch(List<RTransfer> transfers) {
    byte[] newRootHash = accountService.update(transfers, this.lastRootHash);
    batchService.startNewBatch(this.lastRootHash);
    batchService.addToBatch(transfers, newRootHash);
    Batch batch = batchService.getBatchToProve();
    proveService.proveBatch(batch);
    this.lastRootHash = newRootHash;
    // TODO update transfers status
  }
}

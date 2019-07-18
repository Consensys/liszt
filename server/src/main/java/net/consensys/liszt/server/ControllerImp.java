package net.consensys.liszt.server;

import java.util.List;
import net.consensys.liszt.accountmanager.AccountService;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.provemanager.ProveService;
import net.consensys.liszt.transfermanager.Batch;
import net.consensys.liszt.transfermanager.BatchService;
import net.consensys.liszt.transfermanager.RTransferState;
import net.consensys.liszt.transfermanager.TransferService;

public class ControllerImp implements Controller {

  private final TransferService transferService;
  private final AccountService accountService;
  private final BatchService batchService;
  private final ProveService proveService;
  private byte[] lastRootHash;

  public ControllerImp(
      TransferService transferService,
      AccountService accountService,
      BatchService batchService,
      ProveService proveService) {
    this.transferService = transferService;
    this.accountService = accountService;
    this.batchService = batchService;
    this.proveService = proveService;
  }

  @Override
  public boolean addTransfer(RTransfer rtx) {
    if (!accountService.checkBasicValidity(rtx)) return false;

    transferService.addTransfer(rtx);
    List<RTransfer> transfers = transferService.selectRTransfersForNextBatch(lastRootHash);
    if (!transfers.isEmpty()) {
      handleNewBatch(transfers);
    }
    return true;
  }

  @Override
  public void onNewProof() {}

  @Override
  public RTransferState getRTransferStatus(byte[] transferHas) {
    return null;
  }

  private void handleNewBatch(List<RTransfer> transfers) {
    byte[] newRootHash = accountService.update(transfers, lastRootHash);
    batchService.startNewBatch(lastRootHash);
    batchService.addToBatch(transfers, newRootHash);
    Batch batch = batchService.getBatchToProve();
    proveService.proveBatch(batch);
  }
}

package net.consensys.liszt.server;

import net.consensys.liszt.accountmanager.AccountService;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.transfermanager.Batch;
import net.consensys.liszt.transfermanager.BatchService;
import net.consensys.liszt.transfermanager.RTransferState;
import net.consensys.liszt.transfermanager.TransferService;

import java.util.List;

public class ControllerImp implements Controller {

  private final TransferService transferService;
  private final AccountService accountService;
  private final BatchService batchService;
  private byte[] lastRootHash;

  public ControllerImp(TransferService transferService, AccountService accountService, BatchService batchService) {
    this.transferService = transferService;
    this.accountService = accountService;
    this.batchService = batchService;
  }

  @Override
  public boolean addTransfer(RTransfer rtx) {
    if (accountService.checkBasicValidity(rtx)){
      transferService.addTransfer(rtx);
      List<RTransfer> transfers = transferService.selectRTransfersForNextBatch(lastRootHash);
      if (!transfers.isEmpty()){
        handleNewBatch(transfers);
      }
      return true;
    }
    return false;
  }

  @Override
  public void onNewProof() {

  }

  @Override
  public RTransferState getRTransferStatus(byte[] transferHas) {
    return null;
  }

  private void handleNewBatch(List<RTransfer> transfers){
    byte[] newRootHash = accountService.update(transfers, lastRootHash);
    batchService.startNewBatch(lastRootHash);
    batchService.addToBatch(transfers, newRootHash);
    Batch batch = batchService.getBatchToProve();

  }
}

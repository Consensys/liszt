package net.consensys.liszt.server;

import net.consensys.liszt.accountmanager.AccountService;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.transfermanager.RTransferState;
import net.consensys.liszt.transfermanager.TransferService;

public class ControllerImp implements Controller {

  private final TransferService transferService;
  private final AccountService accountService;

  public ControllerImp(TransferService transferService, AccountService accountService) {
    this.transferService = transferService;
    this.accountService = accountService;
  }

  @Override
  public boolean addTransfer(RTransfer rtx) {

    if (accountService.checkBasicValidity(rtx)) {
      return transferService.addTransfer(rtx);
    }
    return false;
  }

  @Override
  public RTransferState getRTransferStatus(byte[] transferHas) {
    return null;
  }
}

package net.consensys.liszt.transactionmanager;

import java.util.List;
import net.consensys.liszt.accountmanager.AccountService;

public class TransactionServiceImpl implements TransactionService {

  private final AccountService accountService;

  public TransactionServiceImpl(AccountService accountService) {
    this.accountService = accountService;
  }

  @Override
  public boolean addTransaction(RTransfer rtx) {
    return accountService.checkBasicValidity(rtx) && rtx.isSigned();
  }

  @Override
  public RTransferState getRTransferStatus(byte[] transferHas) {
    return null;
  }

  @Override
  public List<RTransfer> selectRTransfersForNextBatch(byte[] fatherRootHash) {
    return null;
  }

}

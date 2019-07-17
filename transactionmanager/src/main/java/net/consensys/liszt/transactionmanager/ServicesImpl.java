package net.consensys.liszt.transactionmanager;

import java.util.List;
import net.consensys.liszt.accountmanager.AccountService;

public class ServicesImpl implements Services {

  private final AccountService accountService;

  public ServicesImpl(AccountService accountService) {
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

  @Override
  public void addToBatch(List<RTransfer> transfers, byte[] rootHash) {}

  @Override
  public BatchState startNewBatch(byte[] fatherRootHash) {
    return null;
  }

  @Override
  public Batch getBatchToProve() {
    return null;
  }

  @Override
  public void storeGeneratedProof(byte[] roothash, byte[] proof) {}

  @Override
  public byte[] generateTransaction(byte[] roothash) {
    return new byte[0];
  }
}

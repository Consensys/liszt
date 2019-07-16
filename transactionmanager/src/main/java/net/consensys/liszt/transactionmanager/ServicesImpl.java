package net.consensys.liszt.transactionmanager;

import java.util.List;

public class ServicesImpl implements Services {

  @Override
  public boolean addTransaction(RTransfer rtx) {
    return false;
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

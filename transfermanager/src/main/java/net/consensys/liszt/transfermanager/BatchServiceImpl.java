package net.consensys.liszt.transfermanager;

import java.util.List;
import net.consensys.liszt.core.common.Batch;
import net.consensys.liszt.core.common.RTransfer;

public class BatchServiceImpl implements BatchService {

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

  @Override
  public Batch getBatch(byte[] rootHash) {
    return null;
  }
}

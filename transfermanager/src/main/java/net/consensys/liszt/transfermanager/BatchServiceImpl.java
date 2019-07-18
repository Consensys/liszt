package net.consensys.liszt.transfermanager;

import java.util.List;
import net.consensys.liszt.core.common.Batch;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;

public class BatchServiceImpl implements BatchService {

  @Override
  public void addToBatch(List<RTransfer> transfers, Hash rootHash) {}

  @Override
  public BatchState startNewBatch(Hash fatherRootHash) {
    return null;
  }

  @Override
  public Batch getBatchToProve() {
    return null;
  }

  @Override
  public void storeGeneratedProof(Hash roothash, byte[] proof) {}

  @Override
  public byte[] generateTransaction(Hash roothash) {
    return new byte[0];
  }

  @Override
  public Batch getBatch(Hash rootHash) {
    return null;
  }

  @Override
  public List<BatchState> getBatchesForTransfer(Hash hash) {
    return null;
  }

  @Override
  public void updateBatchStatus(Batch batch, int blockHight, Hash blockHash) {}
}

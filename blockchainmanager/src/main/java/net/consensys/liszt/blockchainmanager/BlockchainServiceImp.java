package net.consensys.liszt.blockchainmanager;

import java.util.List;
import net.consensys.liszt.core.common.Batch;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.Proof;

public class BlockchainServiceImp implements BlockchainService {

  @Override
  public void submit(Batch batch, Proof proof) {}

  @Override
  public byte[] checkInclusion(Hash rootHash) {
    return new byte[0];
  }

  @Override
  public List<LockDone> getLockDone(int rollupId) {
    return null;
  }

  @Override
  public int getLockedDone(int rollupId, Hash txHash) {
    return 0;
  }
}

package net.consensys.liszt.transfermanager;

import java.util.List;
import net.consensys.liszt.core.common.RTransfer;

public class Batch {
  public final byte[] fatherRootHash;
  public final byte[] rootHash;
  public final List<RTransfer> transfers;

  public Batch(byte[] fatherRootHash, byte[] rootHash, List<RTransfer> transfers) {
    this.fatherRootHash = fatherRootHash;
    this.rootHash = rootHash;
    this.transfers = transfers;
  }
}

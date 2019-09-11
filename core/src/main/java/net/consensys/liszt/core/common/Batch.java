package net.consensys.liszt.core.common;

import java.util.List;
import net.consensys.liszt.core.crypto.Hash;

/** Btach of transactions which correspond to state transition fatherRootHash->rootHash */
public class Batch {
  public final Hash fatherRootHash;
  public final Hash rootHash;
  public final List<RTransfer> transfers;
  public final short rollupId;

  public Batch(Hash fatherRootHash, Hash rootHash, List<RTransfer> transfers, short rollupId) {
    this.fatherRootHash = fatherRootHash;
    this.rootHash = rootHash;
    this.transfers = transfers;
    this.rollupId = rollupId;
  }
}

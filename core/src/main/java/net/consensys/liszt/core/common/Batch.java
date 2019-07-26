package net.consensys.liszt.core.common;

import java.util.List;
import java.util.Objects;
import net.consensys.liszt.core.crypto.Hash;

/** Btach of transactions which correspond to state transition fatherRootHash->rootHash */
public class Batch {
  public final Hash fatherRootHash;
  public final Hash rootHash;
  public final List<RTransfer> transfers;

  public Batch(Hash fatherRootHash, Hash rootHash, List<RTransfer> transfers) {
    this.fatherRootHash = fatherRootHash;
    this.rootHash = rootHash;
    this.transfers = transfers;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Batch batch = (Batch) o;
    return Objects.equals(fatherRootHash, batch.fatherRootHash)
        && Objects.equals(rootHash, batch.rootHash)
        && Objects.equals(transfers, batch.transfers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fatherRootHash, rootHash, transfers);
  }
}

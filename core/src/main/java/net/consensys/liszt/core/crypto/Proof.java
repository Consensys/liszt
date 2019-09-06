package net.consensys.liszt.core.crypto;

public class Proof {
  public final Hash rootHash;
  public final int id;
  public Proof(Hash rootHash, int id) {
    this.rootHash = rootHash;
    this.id = id;
  }
}

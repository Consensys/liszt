package net.consensys.liszt.core.crypto;

public class Proof {
  public final byte[] rootHash;

  public Proof(byte[] rootHash) {
    this.rootHash = rootHash;
  }
}

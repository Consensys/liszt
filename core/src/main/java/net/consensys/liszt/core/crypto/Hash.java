package net.consensys.liszt.core.crypto;

import java.util.Arrays;

public class Hash {

  protected final byte[] hash;

  protected Hash(byte[] hash) {
    this.hash = hash;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Hash hash1 = (Hash) o;
    return Arrays.equals(hash, hash1.hash);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(hash);
  }
}

package net.consensys.liszt.core.crypto;

import java.util.Objects;

public class PublicKey {
  private String owner;
  public final Hash hash;

  public PublicKey(String owner) {
    this.owner = owner;
    this.hash = HashUtil.hash(owner);
  }

  public PublicKey(Hash hash) {
    this.hash = hash;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PublicKey publicKey = (PublicKey) o;
    return Objects.equals(hash, publicKey.hash);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hash);
  }

  @Override
  public String toString() {
    return "PublicKey{" + "owner='" + owner + '\'' + ", hash=" + hash.asHex + '}';
  }
}

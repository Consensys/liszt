package net.consensys.liszt.core.crypto;

import java.util.Objects;

public class PublicKey {
  private final String owner;

  public PublicKey(String owner) {
    this.owner = owner;
  }

  @Override
  public String toString() {
    return owner;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PublicKey publicKey = (PublicKey) o;
    return Objects.equals(owner, publicKey.owner);
  }

  @Override
  public int hashCode() {
    return Objects.hash(owner);
  }
}

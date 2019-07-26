package net.consensys.liszt.core.crypto;

public class Signature {
  public boolean validate(PublicKey publicKey, Hash message) {
    return true;
  }

  @Override
  public String toString() {
    return "";
  }
}

package net.consensys.liszt.accountmanager;

import java.math.BigInteger;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.PublicKey;

/** The status of an account. */
public class Account {
  public final PublicKey publicKey;
  public final int id;

  public final BigInteger amount;
  public final int nonce;
  public final Hash hash;
  public final boolean isLock;

  protected Account(
      PublicKey publicKey, int id, BigInteger amount, int nonce, Hash hash, boolean isLock) {
    this.publicKey = publicKey;
    this.id = id;
    this.amount = amount;
    this.nonce = nonce;
    this.hash = hash;
    this.isLock = isLock;
  }
}

package net.consensys.liszt.accountmanager;

import java.math.BigInteger;
import net.consensys.liszt.core.crypto.PublicKey;

/** The status of an account. */
public class Account {
  public final PublicKey publicKey;
  public final int id;

  public final BigInteger amount;
  public final int nonce;

  public Account(PublicKey publicKey, int id, BigInteger amount, int nonce) {
    this.publicKey = publicKey;
    this.id = id;
    this.amount = amount;
    this.nonce = nonce;
  }
}

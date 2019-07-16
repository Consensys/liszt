package net.consensys.liszt.accountmanager;

import java.math.BigDecimal;

/** The status of an account. */
public class AccountState {
  public final byte[] publicKey;
  public final byte[] id;

  public final BigDecimal amount;
  public final int nonce;

  public AccountState(byte[] publicKey, byte[] id, BigDecimal amount, int nonce) {
    this.publicKey = publicKey;
    this.id = id;
    this.amount = amount;
    this.nonce = nonce;
  }
}

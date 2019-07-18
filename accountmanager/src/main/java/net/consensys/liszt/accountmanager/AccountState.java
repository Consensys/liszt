package net.consensys.liszt.accountmanager;

import java.math.BigDecimal;
import net.consensys.liszt.core.crypto.PublicKey;

/** The status of an account. */
public class AccountState {
  public final PublicKey publicKey;
  public final int id;

  public final BigDecimal amount;
  public final int nonce;

  public AccountState(PublicKey publicKey, int id, BigDecimal amount, int nonce) {
    this.publicKey = publicKey;
    this.id = id;
    this.amount = amount;
    this.nonce = nonce;
  }
}

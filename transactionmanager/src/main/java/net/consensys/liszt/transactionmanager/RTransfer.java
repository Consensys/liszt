package net.consensys.liszt.transactionmanager;

import java.math.BigDecimal;

/**
 * Represents a transfer transaction for a rollup, as seen by a user. TODO: choose the rights types
 * for bytes (do as Pantheon)
 */
public class RTransfer {
  public final int nonce;
  public final byte[] from;
  public final byte[] to;
  public final BigDecimal amount;
  public final int rIdFrom;
  public final int rIdTo;
  public final byte[] sig;

  public RTransfer(
      int nonce, byte[] from, byte[] to, BigDecimal amount, int rIdFrom, int rIdTo, byte[] sig) {
    this.nonce = nonce;
    this.from = from;
    this.to = to;
    this.amount = amount;
    this.rIdFrom = rIdFrom;
    this.rIdTo = rIdTo;
    this.sig = sig;
  }
}

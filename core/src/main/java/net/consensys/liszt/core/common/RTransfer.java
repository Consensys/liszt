package net.consensys.liszt.core.common;

import java.math.BigInteger;
import java.util.Objects;
import net.consensys.liszt.core.crypto.PublicKey;
import net.consensys.liszt.core.crypto.Signature;

/** Represents a transfer transaction for a rollup, as seen by a user. for bytes (do as Pantheon) */
public class RTransfer {
  public final int nonce;
  public final PublicKey from;
  public final PublicKey to;
  public final BigInteger amount;
  public final int rIdFrom;
  public final int rIdTo;
  public final Signature sig;

  public RTransfer(
      int nonce,
      PublicKey from,
      PublicKey to,
      BigInteger amount,
      int rIdFrom,
      int rIdTo,
      Signature sig) {
    this.nonce = nonce;
    this.from = from;
    this.to = to;
    this.amount = amount;
    this.rIdFrom = rIdFrom;
    this.rIdTo = rIdTo;
    this.sig = sig;
  }

  public boolean isSigned() {
    return sig.validate(from, hash());
  }

  public byte[] hash() {
    return null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RTransfer rTransfer = (RTransfer) o;
    return nonce == rTransfer.nonce
        && rIdFrom == rTransfer.rIdFrom
        && rIdTo == rTransfer.rIdTo
        && Objects.equals(from, rTransfer.from)
        && Objects.equals(to, rTransfer.to)
        && Objects.equals(amount, rTransfer.amount)
        && Objects.equals(sig, rTransfer.sig);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nonce, from, to, amount, rIdFrom, rIdTo, sig);
  }
}

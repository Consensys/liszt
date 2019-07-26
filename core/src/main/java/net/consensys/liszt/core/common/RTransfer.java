package net.consensys.liszt.core.common;

import java.math.BigInteger;
import java.util.Objects;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.HashUtil;
import net.consensys.liszt.core.crypto.PublicKey;
import net.consensys.liszt.core.crypto.Signature;

/** Represents a transfer transaction for a rollup, as seen by a user */
public class RTransfer {
  public final int nonce;
  public final PublicKey from;
  public final PublicKey to;
  public final BigInteger amount;
  public final int rIdFrom;
  public final int rIdTo;
  public final Signature sig;
  public final Hash hash;

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
    this.hash =
        HashUtil.hash(
            String.valueOf(nonce),
            from.toString(),
            to.toString(),
            amount.toString(),
            String.valueOf(rIdFrom),
            String.valueOf(rIdFrom),
            sig.toString());
  }

  public boolean isSigned() {
    return sig.validate(from, this.hash);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RTransfer rTransfer = (RTransfer) o;
    return hash.equals(rTransfer.hash);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hash);
  }
}

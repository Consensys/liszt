package net.consensys.liszt.blockchainmanager.contract;

import java.math.BigInteger;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.PublicKey;

public class TransferDone {
  public final PublicKey from;
  public final PublicKey to;
  public final BigInteger amount;

  public TransferDone(byte[] from, byte[] to, BigInteger amount) {
    this.from = new PublicKey(new Hash(from));
    this.to = new PublicKey(new Hash(to));
    this.amount = amount;
  }
}

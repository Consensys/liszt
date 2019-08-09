package net.consensys.liszt.blockchainmanager.contract;

import java.math.BigInteger;

public class TransferDone {
  public final String from;
  public final String to;
  public final BigInteger amount;

  public TransferDone(String from, String to, BigInteger amount) {
    this.from = from;
    this.to = to;
    this.amount = amount;
  }
}

package net.consensys.liszt.blockchainmanager.contract;

import java.math.BigInteger;

public class TransferComplete {
  final String from;
  final String to;
  final BigInteger amount;

  final XTransfer other;

  public TransferComplete(String from, String to, BigInteger amount, XTransfer other) {
    this.from = from;
    this.to = to;
    this.amount = amount;
    this.other = other;
  }
}

package net.consensys.liszt.blockchainmanager.contract;

import java.math.BigInteger;

public class XTransfer {
  final String from;
  final String to;
  final BigInteger amount;
  final short sourceRollupId;
  final short targetRollupId;

  public XTransfer(
      String from, String to, BigInteger amount, short sourceRollupId, short targetRollupId) {
    this.from = from;
    this.to = to;
    this.amount = amount;
    this.sourceRollupId = sourceRollupId;
    this.targetRollupId = targetRollupId;
  }
}

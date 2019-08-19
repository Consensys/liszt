package net.consensys.liszt.server.dto;

import java.math.BigInteger;

public class AcccountInfo {
  public final String owner;
  public final BigInteger balance;
  public final int nonce;
  public final boolean isLock;

  public AcccountInfo(String owner, BigInteger balance, int nonce,boolean isLock) {
    this.owner = owner;
    this.balance = balance;
    this.nonce = nonce;
    this.isLock = isLock;

  }
}

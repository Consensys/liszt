package net.consensys.liszt.server.dto;

import java.math.BigInteger;

public class Acccount {
  public final String owner;
  public final BigInteger balance;
  public final int nonce;

  public Acccount(String owner, BigInteger balance, int nonce) {
    this.owner = owner;
    this.balance = balance;
    this.nonce = nonce;
  }
}

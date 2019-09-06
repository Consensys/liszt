package net.consensys.liszt.server.dto;

import java.math.BigInteger;

public class Transfer {
  public String from;
  public String to;
  public BigInteger amount;
  public int nonce;
  public short rIdFrom;
  public short rIdTo;
  public String hashOfThePendingTransfer;
  public int timeout;


  public Transfer() {}
}

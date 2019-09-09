package net.consensys.liszt.server.dto;

import java.math.BigInteger;

public class NodeInfo {
  final BigInteger blockHeight;

  public NodeInfo(BigInteger blockHeight) {
    this.blockHeight = blockHeight;
  }
}

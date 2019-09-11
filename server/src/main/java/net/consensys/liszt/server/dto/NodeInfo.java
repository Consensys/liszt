package net.consensys.liszt.server.dto;

import java.math.BigInteger;

public class NodeInfo {
  final BigInteger blockHeight;
  final String lastRootHash;

  public NodeInfo(BigInteger blockHeight, String lastRootHash) {
    this.blockHeight = blockHeight;
    this.lastRootHash = lastRootHash;
  }
}

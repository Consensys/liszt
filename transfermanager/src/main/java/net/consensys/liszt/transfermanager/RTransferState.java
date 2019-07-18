package net.consensys.liszt.transfermanager;

import java.util.List;

public class RTransferState {
  public final byte[] transferHash;

  /**
   * The states of all batches that included this transfer, as known by the operator. Most of the
   * time there should be only one such state.
   */
  public final List<BatchState> batchStates;

  public RTransferState(byte[] transferHash, List<BatchState> batchStates) {
    this.transferHash = transferHash;
    this.batchStates = batchStates;
  }
}

package net.consensys.liszt.transfermanager;

import java.util.List;
import net.consensys.liszt.core.common.RTransfer;

public class RTransferState {
  public final RTransfer transfer;

  /**
   * The states of all batches that included this transfer, as known by the operator. Most of the
   * time there should be only one such state.
   */
  public final List<BatchStatus> batchStates;

  public RTransferState(RTransfer transfer, List<BatchStatus> batchStates) {
    this.transfer = transfer;
    this.batchStates = batchStates;
  }
}

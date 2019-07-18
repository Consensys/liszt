package net.consensys.liszt.server;

import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.transfermanager.RTransferState;


public interface Controller {

  /**
   * Add a transfer to the list of transfers.
   *
   * @param rtx - the transfer
   * @return true if the transfer can be included, false otherwise.
   */
  boolean addTransfer(RTransfer rtx);

  /** @return the state of the transfer. */
  RTransferState getRTransferStatus(byte[] transferHas);

  /**
   *
   */
  void onNewProof();
}

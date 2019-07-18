package net.consensys.liszt.transfermanager;

import java.util.List;
import net.consensys.liszt.core.common.RTransfer;

public interface TransferService {

  /**
   * Add a transfer to the list of transfer to include, as soon as possible, in the rollup.
   *
   * @param rtx - the transfer
   * @return true if the transfer can be included, false otherwise.
   */
  boolean addTransfer(RTransfer rtx);

  /** @return the state of the transfer. */
  RTransferState getRTransferStatus(byte[] transferHas);

  /**
   * Select the transfer for the next batch.
   *
   * @param fatherRootHash - the starting point
   * @return the list of transfer selected.
   */
  List<RTransfer> selectRTransfersForNextBatch(byte[] fatherRootHash);
}

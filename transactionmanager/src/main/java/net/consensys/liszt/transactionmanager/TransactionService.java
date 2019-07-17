package net.consensys.liszt.transactionmanager;

import net.consensys.liszt.core.common.RTransfer;

import java.util.List;

public interface TransactionService {

  /**
   * Add a transfer to the list of transfer to include, as soon as possible, in the rollup.
   *
   * @param rtx - the transfer
   * @return true if the transfer can be included, false otherwise.
   */
  boolean addTransaction(RTransfer rtx);

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

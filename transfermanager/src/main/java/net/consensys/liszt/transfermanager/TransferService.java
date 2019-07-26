package net.consensys.liszt.transfermanager;

import java.util.List;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;

public interface TransferService {

  /**
   * Add a transfer to the list of transfer to include, as soon as possible, in the rollup.
   *
   * @param rtx - the transfer
   */
  void addTransfer(RTransfer rtx);

  /** @return the state of the transfer. */
  RTransferState getRTransferStatus(Hash transferHash);

  /**
   * Select the transfer for the next batch.
   *
   * @param fatherRootHash - the starting point
   * @param invalidTransfers - transfers which need to be excluded
   * @return the list of transfer selected.
   */
  List<RTransfer> selectRTransfersForNextBatch(
      Hash fatherRootHash, List<RTransfer> invalidTransfers);
}

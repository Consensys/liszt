package net.consensys.liszt.server;

import net.consensys.liszt.core.common.Batch;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.Proof;
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
  RTransferState getRTransferStatus(RTransfer transfer);

  /**
   * Callback invoked on every new proof generated
   *
   * @param proof
   */
  void onNewProof(Proof proof);

  /**
   * Callback invoked on chain reorganization
   *
   * @param rootHash rootHash of the new rollup
   */
  void onChainReorg(Hash rootHash);

  /**
   * Callback invoked when batch and corresponding proof are included in a block
   *
   * @param batch, blockHight, blockHash
   */
  void onBatchIncluded(Batch batch, int blockHight, Hash blockHash);
}

package net.consensys.liszt.transfermanager;

import java.util.List;
import net.consensys.liszt.core.common.Batch;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;

public interface BatchService {

  /**
   * Start a new batch
   *
   * @param fatherRootHash - the father for this batch
   * @return todo
   */
  BatchStatus startNewBatch(Hash fatherRootHash, Hash rootHash, List<RTransfer> transfers);

  /** @return a batch waiting to be proven. */
  Batch getBatchToProve();

  /**
   * @param rootHash, account balance root hash corresponding to the given batch
   * @return Batch
   */
  Batch getBatch(Hash rootHash);

  /**
   * @param transfer
   * @return List of batch states corresponding to a given transfer
   */
  List<BatchStatus> getBatchesForTransfer(RTransfer transfer);

  /**
   * Updates status corresponding to the given batch
   *
   * @param batch, blockHight, blockHash
   */
  void updateBatchStatus(Batch batch, int blockHight, Hash blockHash);
}

package net.consensys.liszt.transfermanager;

import java.util.List;
import net.consensys.liszt.core.common.Batch;
import net.consensys.liszt.core.common.RTransfer;

public interface BatchService {

  /**
   * Mark the transfers as included.
   *
   * @param transfers - the list of transfers
   * @param rootHash - the root hash of the batch in which they are included.
   */
  void addToBatch(List<RTransfer> transfers, byte[] rootHash);

  /**
   * Start a new batch
   *
   * @param fatherRootHash - the father for this batch
   * @return todo
   */
  BatchState startNewBatch(byte[] fatherRootHash);

  /** @return a batch waiting to be proven. */
  Batch getBatchToProve();

  /**
   * Checks the proof is valid. If so the proof is kept and will be used to generate an ethereum tx.
   */
  void storeGeneratedProof(byte[] roothash, byte[] proof);

  /**
   * Generates an ethereum transaction for the corresponding Batch. The proof must have been
   * generated already.
   */
  byte[] generateTransaction(byte[] roothash);

  /**
   * @param rootHash, account balance root hash corresponding to the given batch
   * @return Batch
   */
  Batch getBatch(byte[] rootHash);

  /**
   * @param hash
   * @return List of batch states corresponding to a given transfer hash
   */
  List<BatchState> getBatchesForTransfer(byte[] hash);

  /**
   * Updates status corresponding to the given batch
   *
   * @param batch, blockHight, blockHash
   */
  void updateBatchStatus(Batch batch, int blockHight, byte[] blockHash);
}

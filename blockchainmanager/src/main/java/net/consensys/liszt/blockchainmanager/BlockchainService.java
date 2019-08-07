package net.consensys.liszt.blockchainmanager;

import net.consensys.liszt.core.common.Batch;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.Proof;

public interface BlockchainService {

  /** Starts local ethereum client and deploys Liszt smart contract */
  void startLocalNode() throws Exception;

  /**
   * Update smart contract with proof and corresponding data. It must be safe to call this method
   * multiple times with the same arguments.
   */
  void submit(Batch batch, Proof proof) throws Exception;

  /**
   * Check if a batch, identified by its roothash, has been included by the blokchain
   *
   * @return the hash of the block where the transaction was included, if any
   */
  byte[] checkInclusion(Hash rootHash);

  /**
   * Get the list of cross rollup transfers with this rollup id as the destination that are in
   * locked done but not in transfer done state. This is called by the operator when he starts a
   * batch, so see if there are transfers that be can done in the current time limit.
   */
  // List<LockDone> getLockDone(int rollupId);

  /**
   * Check if there is a lock and the transfer is not done for this transfer.
   *
   * @return the timeout (a block height) if the locked in done, 0 otherwise
   */
  long getLockedDone(int rollupId, Hash txHash) throws Exception;
}

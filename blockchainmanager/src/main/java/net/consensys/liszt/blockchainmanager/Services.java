package net.consensys.liszt.blockchainmanager;

import java.util.List;

public interface Services {

  /**
   * Create, sign and submit the ethereum transaction of the blockchain. Responsible to ensure that
   * we don't send two transactions for the same rollup update
   */
  void submit(
      int rollupId, byte[] fatherRootHash, byte[] rootHash, byte[] transfers, byte[] proofs);

  /**
   * Check if a batch, identified by its roothash, has been included by the blochain
   *
   * @return the hash of the block where the transaction was included, if any
   */
  byte[] checkInclusion(byte[] rootHash);


  /**
   * Get the list of cross transfers with this rollup id as the destination that are in locked done but not in
   *  transfer done state. This is called by the operator when he starts a batch, so see if there
   *  are transfers that can done in the current time limit.
   */
  List<LockDone> getLockDone(int rollupId);


  /**
   * Check if there is a lock and the transfer is not done for this transfer.
   * @return the timeout (a block height) if the locked in done, 0 otherwise
   */
  int getLockedDone(int rollupId, byte[] txHash);
}

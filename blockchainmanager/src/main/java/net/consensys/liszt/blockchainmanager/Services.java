package net.consensys.liszt.blockchainmanager;

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
}

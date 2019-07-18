package net.consensys.liszt.transfermanager;

import java.util.Date;

/**
 * A status for a batch. A batch can have multiple status, for example if the blockchain forked. The
 * operators return all the status they know.
 */
public class BatchState {

  /** The root hash is used as the identifier for a batch. */
  public final byte[] roothash;

  /** Date when we started to generate the proof, if any. */
  public final Date proofStartAt;

  /** Date when we finished to generate the proof, if any. */
  public final Date proofEndAt;

  /**
   * The hash of the transaction on which this batch is written to Ethereum, if any. There can be
   * only one transaction for a given batch.
   */
  public final byte[] ethTxHash;

  /** The block hash of the block where the corresponding transaction was inserted if any. */
  public final byte[] blockhash;

  /** The block height where the batch was stored, if any. */
  public final long blockheigth;

  public BatchState(
      byte[] roothash,
      Date proofStartAt,
      Date proofEndAt,
      byte[] ethTxHash,
      byte[] blockhash,
      long blockheigth) {
    this.roothash = roothash;
    this.proofStartAt = proofStartAt;
    this.proofEndAt = proofEndAt;
    this.ethTxHash = ethTxHash;
    this.blockhash = blockhash;
    this.blockheigth = blockheigth;
  }
}

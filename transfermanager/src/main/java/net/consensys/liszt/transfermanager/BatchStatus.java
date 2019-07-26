package net.consensys.liszt.transfermanager;

import java.util.Date;
import java.util.Optional;
import net.consensys.liszt.core.crypto.Hash;

/**
 * A status for a batch. A batch can have multiple status, for example if the blockchain forked. The
 * operators return all the status they know.
 */
public class BatchStatus {

  /** The root hash is used as the identifier for a batch. */
  public final Hash roothash;

  /** Date when we started to generate the proof, if any. */
  public final Optional<Date> proofStartAt;

  /** Date when we finished to generate the proof, if any. */
  public final Optional<Date> proofEndAt;

  /** The block hash of the block where the corresponding transaction was inserted if any. */
  public final Optional<Hash> blockhash;

  /** The block height where the batch was stored, if any. */
  public final Optional<Long> blockheigth;

  public BatchStatus(
      Hash roothash,
      Optional<Date> proofStartAt,
      Optional<Date> proofEndAt,
      Optional<Hash> blockhash,
      Optional<Long> blockheigth) {
    this.roothash = roothash;
    this.proofStartAt = proofStartAt;
    this.proofEndAt = proofEndAt;
    this.blockhash = blockhash;
    this.blockheigth = blockheigth;
  }
}

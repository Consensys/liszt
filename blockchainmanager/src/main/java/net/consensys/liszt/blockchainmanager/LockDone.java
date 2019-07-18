package net.consensys.liszt.blockchainmanager;

public class LockDone {

  /** The hash of the initial cross transfer transaction. */
  public final byte[] xTransferHash;

  /** The id of the 'from' rollup. */
  public final int rollupId;

  /**
   * The timeout of this cross rollup transaction. This allows the operator to decide if it makes
   * sense to include a transfer transaction in his next batch.
   */
  public final int timeout;

  public LockDone(byte[] xTransferHash, int rollupId, int timeout) {
    this.xTransferHash = xTransferHash;
    this.rollupId = rollupId;
    this.timeout = timeout;
  }
}

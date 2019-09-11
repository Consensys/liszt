package net.consensys.liszt.transfermanager;

import java.util.*;
import net.consensys.liszt.core.common.Batch;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;

public class BatchServiceImpl implements BatchService {
  private final Map<Hash, Batch> batches;
  private final Map<Hash, List<BatchStatus>> batchStatusesForTransfer;
  private final Queue<Batch> batchesToProve;
  private final short rollupId;

  public BatchServiceImpl(short rollupId) {
    batches = new HashMap<>();
    batchStatusesForTransfer = new HashMap<>();
    batchesToProve = new LinkedList<>();
    this.rollupId = rollupId;
  }

  @Override
  public BatchStatus startNewBatch(Hash fatherRootHash, Hash rootHash, List<RTransfer> transfers) {
    Batch batch = new Batch(fatherRootHash, rootHash, transfers, this.rollupId);
    batches.put(rootHash, batch);
    batchesToProve.add(batch);

    BatchStatus batchStatus =
        new BatchStatus(
            rootHash, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());

    for (RTransfer transfer : transfers) {
      List<BatchStatus> statuses = batchStatusesForTransfer.get(transfer.hash);
      if (statuses == null) {
        statuses = new ArrayList<>();
      }
      statuses.add(batchStatus);
      batchStatusesForTransfer.put(transfer.hash, statuses);
    }
    return batchStatus;
  }

  @Override
  public Batch getBatchToProve() {
    return batchesToProve.poll();
  }

  @Override
  public Batch getBatch(Hash rootHash) {
    return batches.get(rootHash);
  }

  @Override
  public List<BatchStatus> getBatchesForTransfer(RTransfer transfer) {
    return batchStatusesForTransfer.get(transfer.hash);
  }

  @Override
  public void updateBatchStatus(Batch batch, int blockHight, Hash blockHash) {
    // TODO
  }
}

package net.consensys.liszt.transfermanager;

import java.util.ArrayList;
import java.util.List;
import net.consensys.liszt.core.common.Batch;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.HashUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BatchServiceTest {

  private BatchService batchService;
  private List<RTransfer> transfers;
  private Hash rootHash;

  @Before
  public void setUp() {
    batchService = new BatchServiceImpl((short) 0);
    Hash previousRootHash = HashUtil.hash("0");
    rootHash = HashUtil.hash("1");
    transfers = new ArrayList<>();
    transfers.add(TransferTestUtil.createMockTransfer(0));
    transfers.add(TransferTestUtil.createMockTransfer(1));
    transfers.add(TransferTestUtil.createMockTransfer(2));
    batchService.startNewBatch(previousRootHash, rootHash, transfers);
  }

  @Test
  public void producesAndStoreBatchToProve() {
    Batch batch = batchService.getBatchToProve();
    Assert.assertEquals(batch.transfers.size(), transfers.size());
    Batch nextBatch = batchService.getBatchToProve();
    Assert.assertNull(nextBatch);
    Batch oldBatch = batchService.getBatch(batch.rootHash);
    Assert.assertEquals(oldBatch, batch);
  }

  @Test
  public void retrievesCorrectBatchStatusForTransaction() {
    List<BatchStatus> batchStatus =
        batchService.getBatchesForTransfer(TransferTestUtil.createMockTransfer(1));
    Assert.assertEquals(rootHash, batchStatus.get(0).roothash);
  }
}

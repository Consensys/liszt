package net.consensys.liszt.transfermanager;

import java.util.ArrayList;
import java.util.List;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.HashUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TransferServiceTest {
  private final int BATCH_SIZE = 3;
  private final int TRANSFER_COUNT = 7;
  TransferService transferService;

  @Before
  public void setUp() {
    transferService = new TransferServiceImpl(3);
    transfers().forEach(t -> transferService.addTransfer(t));
  }

  @Test
  public void transferIsCached() {
    Hash hash1 = HashUtil.hash("1");
    List<RTransfer> transfersForBatch1 =
        transferService.selectRTransfersForNextBatch(hash1, new ArrayList<>());
    Assert.assertEquals(BATCH_SIZE, transfersForBatch1.size());

    Hash hash2 = HashUtil.hash("2");
    List<RTransfer> transfersForBatch2 =
        transferService.selectRTransfersForNextBatch(hash2, new ArrayList<>());
    Assert.assertEquals(BATCH_SIZE, transfersForBatch2.size());

    List<RTransfer> cachedTransfers =
        transferService.selectRTransfersForNextBatch(hash1, new ArrayList<>());
    Assert.assertEquals(cachedTransfers, transfersForBatch1);

    Hash hash3 = HashUtil.hash("3");
    List<RTransfer> remainingTransfers =
        transferService.selectRTransfersForNextBatch(hash3, new ArrayList<>());
    Assert.assertTrue(remainingTransfers.isEmpty());
  }

  @Test
  public void invalidTransfersAreFilteredOut() {
    List<RTransfer> invalidTransfers = new ArrayList<>();
    invalidTransfers.add(TransferTestUtil.createMockTransfer(1));
    invalidTransfers.add(TransferTestUtil.createMockTransfer(2));
    Hash hash1 = HashUtil.hash("1");
    List<RTransfer> transfersForBatch1 =
        transferService.selectRTransfersForNextBatch(hash1, invalidTransfers);
    Assert.assertEquals(1, transfersForBatch1.size());
  }

  private List<RTransfer> transfers() {
    List<RTransfer> transfers = new ArrayList<>();
    for (int i = 0; i < TRANSFER_COUNT; i++) {
      transfers.add(TransferTestUtil.createMockTransfer(i));
    }
    return transfers;
  }
}

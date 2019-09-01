package net.consensys.liszt.transfermanager;

import java.util.ArrayList;
import java.util.List;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;

public class SimpleTransferServiceImpl implements TransferService {

  private final List<RTransfer> pendingTransfers = new ArrayList<>();
  private final int batchSize;

  public SimpleTransferServiceImpl(int batchSize) {
    this.batchSize = batchSize;
  }

  @Override
  public void addTransfer(RTransfer rtx) {
    pendingTransfers.add(rtx);
  }

  @Override
  public RTransferState getRTransferStatus(Hash transferHash) {
    return null;
  }

  @Override
  public List<RTransfer> selectRTransfersForNextBatch(
      Hash fatherRootHash, List<RTransfer> invalidTransfers) {
    List<RTransfer> transfers = new ArrayList<>();
    int size = pendingTransfers.size();
    if (size >= batchSize) {
      while (size > 0) {
        transfers.add(pendingTransfers.remove(0));
        size--;
      }
    }
    return transfers;
  }
}

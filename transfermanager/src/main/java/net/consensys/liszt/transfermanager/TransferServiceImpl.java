package net.consensys.liszt.transfermanager;

import java.util.*;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;

public class TransferServiceImpl implements TransferService {

  private final List<RTransfer> pendingTransfers = new ArrayList<>();
  private final Map<Hash, List<RTransfer>> selectedTransfers = new HashMap<>();
  private final int batchSize;

  public TransferServiceImpl(int batchSize) {
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
    List<RTransfer> selectedInThePast = selectedTransfers.get(fatherRootHash);
    if (selectedInThePast != null && invalidTransfers.isEmpty()) {
      return selectedInThePast;
    } else if (pendingTransfers.size() >= batchSize) {
      if (selectedInThePast != null) {
        List<RTransfer> selectedInThePastCpy = new ArrayList<>(selectedInThePast);
        Collections.reverse(selectedInThePastCpy);
        for (RTransfer t : selectedInThePastCpy) {
          boolean isInvalid = invalidTransfers.contains(t);
          if (!isInvalid) {
            pendingTransfers.add(0, t);
          }
        }
      }
      List<RTransfer> front = new ArrayList<>();
      int i = 0;
      while (i < batchSize) {
        RTransfer t = pendingTransfers.remove(0);
        boolean isInvalid = invalidTransfers.contains(t);
        if (!isInvalid) {
          front.add(t);
        }
        i++;
      }
      selectedTransfers.put(fatherRootHash, front);
      return front;
    }
    return new ArrayList<>();
  }
}

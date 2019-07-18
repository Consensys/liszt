package net.consensys.liszt.transfermanager;

import java.util.*;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;

public class TransferServiceImpl implements TransferService {

  @Override
  public void addTransfer(RTransfer rtx) {}

  @Override
  public RTransferState getRTransferStatus(Hash transferHas) {
    return null;
  }

  @Override
  public List<RTransfer> selectRTransfersForNextBatch(Hash fatherRootHash) {
    return new ArrayList<>();
  }
}

package net.consensys.liszt.transfermanager;

import java.util.List;
import net.consensys.liszt.core.common.RTransfer;

public class TransferServiceImpl implements TransferService {

  @Override
  public boolean addTransfer(RTransfer rtx) {
    return rtx.isSigned();
  }

  @Override
  public RTransferState getRTransferStatus(byte[] transferHas) {
    return null;
  }

  @Override
  public List<RTransfer> selectRTransfersForNextBatch(byte[] fatherRootHash) {
    return null;
  }
}

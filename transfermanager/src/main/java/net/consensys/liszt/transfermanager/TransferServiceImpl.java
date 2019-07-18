package net.consensys.liszt.transfermanager;

import java.util.List;
import net.consensys.liszt.core.common.RTransfer;

public class TransferServiceImpl implements TransferService {

  @Override
  public void addTransfer(RTransfer rtx) {

   
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

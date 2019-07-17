package net.consensys.liszt.transactionmanager;

import java.util.List;
import net.consensys.liszt.core.common.RTransfer;

public class TransactionServiceImpl implements TransactionService {

  @Override
  public boolean addTransaction(RTransfer rtx) {
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

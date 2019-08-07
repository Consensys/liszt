package net.consensys.liszt.blockchainmanager.contract;

import java.math.BigInteger;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public class Liszt {

  private final LisztContract lisztContract;

  public Liszt(LisztContract lisztContract) {
    this.lisztContract = lisztContract;
  }

  public TransactionReceipt updateLockDone(RTransfer t) throws Exception {
    return lisztContract
        .updateLockDone(
            t.from.owner,
            t.to.owner,
            t.amount,
            BigInteger.valueOf(t.rIdFrom),
            BigInteger.valueOf(t.rIdTo),
            BigInteger.valueOf(t.timeout),
            t.hash.asHex)
        .send();
  }

  public long lockDoneTimeout(int rollupId, Hash txHash) throws Exception {
    long timeout =
        lisztContract.lockTimeout(BigInteger.valueOf(rollupId), txHash.asHex).send().longValue();
    return timeout;
  }

  public String addr() {
    return lisztContract.getContractAddress();
  }

  /*
  public void updateTransferDone(TransferComplete t) throws Exception {
      lisztContract.updateTransferDone(t.from,
              t.to,
              t.amount,
              t.other.from,
              t.other.to,
              t.other.amount,
              BigInteger.valueOf(t.other.sourceRollupId),
              BigInteger.valueOf(t.other.targetRollupId)
      ).send();
  }


  public boolean transferDoneContains(XTransfer t) throws Exception {
      return lisztContract.lockDoneContains(t.from,
              t.to,
              t.amount,
              BigInteger.valueOf(t.sourceRollupId),
              BigInteger.valueOf(t.targetRollupId)
      ).send();
  }*/

}

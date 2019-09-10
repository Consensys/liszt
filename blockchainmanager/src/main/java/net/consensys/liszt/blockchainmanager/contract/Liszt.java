package net.consensys.liszt.blockchainmanager.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;
import org.web3j.tuples.generated.Tuple4;

public class Liszt {

  private final LisztContract lisztContract;

  public Liszt(LisztContract lisztContract) {
    this.lisztContract = lisztContract;
  }

  public void updateLockDone(List<RTransfer> transfers) throws Exception {
    List<BigInteger> sourceRollupList = new ArrayList<>();
    List<BigInteger> targetRollupList = new ArrayList<>();
    List<BigInteger> timeoutList = new ArrayList<>();
    List<byte[]> hashList = new ArrayList<>();

    for (RTransfer t : transfers) {
      sourceRollupList.add(BigInteger.valueOf(t.rIdFrom));
      targetRollupList.add(BigInteger.valueOf(t.rIdTo));
      timeoutList.add(BigInteger.valueOf(t.timeout));
      hashList.add(t.hash.hash);
    }

    if (!sourceRollupList.isEmpty()) {
      lisztContract
          .updateLockDone(sourceRollupList, targetRollupList, timeoutList, hashList)
          .send();
    }
  }

  public long lockDoneTimeout(int rollupId, Hash txHash) throws Exception {
    long timeout =
        lisztContract.lockTimeout(BigInteger.valueOf(rollupId), txHash.hash).send().longValue();
    return timeout;
  }

  public String addr() {
    return lisztContract.getContractAddress();
  }

  public void updateTransferDone(List<RTransfer> transfers) throws Exception {
    List<BigInteger> rollupIdList = new ArrayList<>();
    List<byte[]> fromList = new ArrayList<>();
    List<byte[]> toList = new ArrayList<>();
    List<BigInteger> amountList = new ArrayList<>();
    List<byte[]> hashList = new ArrayList<>();

    for (RTransfer t : transfers) {
      rollupIdList.add(BigInteger.valueOf(t.rIdFrom));
      fromList.add(t.from.hash.hash);
      toList.add(t.to.hash.hash);
      amountList.add(t.amount);
      hashList.add(new Hash(t.hashOfThePendingTransfer.get()).hash);
    }
    if (!rollupIdList.isEmpty()) {
      lisztContract.updateTransferDone(rollupIdList, fromList, toList, amountList, hashList).send();
    }
  }

  public TransferDone transferDone(short rollupId, Hash hash) throws Exception {
    Tuple4<byte[], byte[], BigInteger, Boolean> done =
        lisztContract.transferDone(BigInteger.valueOf(rollupId), hash.hash).send();

    return new TransferDone(done.getValue1(), done.getValue2(), done.getValue3());
  }
}

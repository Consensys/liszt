package net.consensys.liszt.blockchainmanager.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.Proof;
import org.web3j.tuples.generated.Tuple4;

public class Liszt {

  private final LisztContract lisztContract;

  public Liszt(LisztContract lisztContract) {
    this.lisztContract = lisztContract;
  }

  public long lockDoneTimeout(int rollupId, Hash txHash) throws Exception {
    long timeout =
        lisztContract.lockTimeout(BigInteger.valueOf(rollupId), txHash.hash).send().longValue();
    return timeout;
  }

  public String addr() {
    return lisztContract.getContractAddress();
  }

  public void update(List<RTransfer> transfers, short rollupId, Hash lastRootHash, Proof proof)
      throws Exception {
    List<byte[]> fromList = new ArrayList<>();
    List<byte[]> toList = new ArrayList<>();
    List<BigInteger> amountList = new ArrayList<>();
    List<BigInteger> targetRollupList = new ArrayList<>();
    List<byte[]> hashList = new ArrayList<>();
    List<BigInteger> timeoutList = new ArrayList<>();
    List<byte[]> pendingTransferHashList = new ArrayList<>();

    for (RTransfer t : transfers) {
      fromList.add(t.from.hash.hash);
      toList.add(t.to.hash.hash);
      amountList.add(t.amount);
      targetRollupList.add(BigInteger.valueOf(t.rIdTo));
      hashList.add(t.hash.hash);
      timeoutList.add(BigInteger.valueOf(t.timeout));
      Optional<String> pendingTransferHash = t.hashOfThePendingTransfer;

      if (pendingTransferHash.equals(Optional.empty())) {
        byte[] bytes = new byte[32];
        pendingTransferHashList.add(bytes);
      } else {
        pendingTransferHashList.add(new Hash(pendingTransferHash.get()).hash);
      }
    }

    lisztContract
        .update(
            fromList,
            toList,
            amountList,
            targetRollupList,
            hashList,
            timeoutList,
            pendingTransferHashList,
            BigInteger.valueOf(rollupId),
            lastRootHash.hash)
        .send();
  }

  public TransferDone transferDone(short rollupId, Hash hash) throws Exception {
    Tuple4<byte[], byte[], BigInteger, Boolean> done =
        lisztContract.transferDone(BigInteger.valueOf(rollupId), hash.hash).send();

    return new TransferDone(done.getValue1(), done.getValue2(), done.getValue3());
  }
}

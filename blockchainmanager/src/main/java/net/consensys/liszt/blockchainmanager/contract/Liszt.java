package net.consensys.liszt.blockchainmanager.contract;

import java.math.BigInteger;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;

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

  public void updateTransferDone(RTransfer t) throws Exception {
    lisztContract
        .updateTransferDone(
            BigInteger.valueOf(t.rIdFrom),
            t.from.owner,
            t.to.owner,
            t.amount,
            t.hashOfThePendingTransfer.get())
        .send();
  }

  public TransferDone transferDone(short rollupId, Hash hash) throws Exception {
    Tuple4<String, String, BigInteger, Boolean> done =
        lisztContract.transferDone(BigInteger.valueOf(rollupId), hash.asHex).send();
    return new TransferDone(done.getValue1(), done.getValue2(), done.getValue3());
  }
}

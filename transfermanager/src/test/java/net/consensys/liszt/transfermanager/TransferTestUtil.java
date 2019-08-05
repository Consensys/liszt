package net.consensys.liszt.transfermanager;

import java.math.BigInteger;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.PublicKey;
import net.consensys.liszt.core.crypto.Signature;

public class TransferTestUtil {
  public static RTransfer createMockTransfer(int i) {
    short rid = 1;
    return new RTransfer(
        i,
        new PublicKey("Test1"),
        new PublicKey("Test2"),
        BigInteger.valueOf(1),
        rid,
        rid,
        new Signature());
  }
}

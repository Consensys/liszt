package net.consensys.liszt.server;

import java.math.BigInteger;
import java.util.Optional;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.PublicKey;
import net.consensys.liszt.core.crypto.Signature;

public class TestUtils {

  static PublicKey alice = new PublicKey("Alice");
  static PublicKey bob = new PublicKey("Bob");
  static PublicKey zac = new PublicKey("Zac");
  static PublicKey adam = new PublicKey("Adam");

  static RTransfer createMockTransferFromAliceToBob(int i, BigInteger amount) {
    short rid = 0;
    return new RTransfer(i, alice, bob, amount, rid, rid, new Signature(), 100, Optional.empty());
  }

  static RTransfer createMockXTransferFromAliceToBob(int i, BigInteger amount) {
    short rid1 = 0;
    short rid2 = 1;
    return new RTransfer(i, alice, bob, amount, rid1, rid2, new Signature(), 100, Optional.empty());
  }
}

package net.consensys.liszt.server;

import java.math.BigInteger;
import java.util.Optional;
import net.consensys.liszt.accountmanager.Account;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.PublicKey;
import net.consensys.liszt.core.crypto.Signature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LisztMultiOperatorTest {

  private LisztManager lisztManager1;
  private LisztManager lisztManager2;

  @Before
  public void setUp() throws InterruptedException {
    short rid1 = 0;
    short rid2 = 1;

    lisztManager1 = new LisztManagerImp(rid1, rid2);
    Thread.sleep(2000);
    lisztManager2 = new LisztManagerImp(rid2, rid1);
  }

  @Test
  public void operatorsUpdateLockDone() throws Exception {
    for (int i = 0; i < 3; i++) {
      boolean isValid =
          lisztManager1.addTransfer(
              TestUtils.createMockXTransferFromAliceToBob(i, BigInteger.valueOf(5)));
      Assert.assertTrue(isValid);
    }
    Account aliceAcc = lisztManager1.getAccount(TestUtils.alice);
    Assert.assertEquals(aliceAcc.amount, BigInteger.valueOf(85));
    Account bobAcc = lisztManager1.getAccount(TestUtils.bob);
    Assert.assertEquals(bobAcc.amount, BigInteger.valueOf(100));

    for (int i = 0; i < lisztManager1.getLockAccounts().size(); i++) {
      Account acc = lisztManager1.getLockAccounts().get(i);
      Assert.assertEquals(acc.amount, BigInteger.valueOf(5));
      RTransfer rTransfer = TestUtils.createMockXTransferFromAliceToBob(i, BigInteger.valueOf(5));
      Assert.assertEquals(acc.publicKey.hash, rTransfer.hash);
    }

    RTransfer rTransfer = TestUtils.createMockXTransferFromAliceToBob(1, BigInteger.valueOf(5));
    long timeout = lisztManager1.getLockDoneTimeout(rTransfer.hash);
    Assert.assertEquals(timeout, 100);

    long timeout2 = lisztManager2.getLockDoneTimeout(rTransfer.hash);
    Assert.assertEquals(timeout2, 0);

    Account lockedAcc = lisztManager1.getAccount(rTransfer.hash.asHex);
    Assert.assertEquals(lockedAcc.amount, BigInteger.valueOf(5));

    RTransfer complete =
        new RTransfer(
            3,
            TestUtils.zac,
            TestUtils.bob,
            BigInteger.valueOf(5),
            (short) 1,
            (short) 1,
            new Signature(),
            100,
            Optional.of(rTransfer.hash.asHex));

    boolean isValid = lisztManager2.addTransfer(complete);

    Assert.assertTrue(isValid);

    for (int i = 0; i < 3; i++) {
      isValid =
          lisztManager2.addTransfer(
              TestUtils.createMockTransferFromAliceToBob(i, BigInteger.valueOf(0)));
      Assert.assertTrue(isValid);
    }

    Account rtAcc = lisztManager1.getAccount(complete.hashOfThePendingTransfer.get());
    Assert.assertEquals(rtAcc.amount, BigInteger.valueOf(5));

    RTransfer unlock =
        new RTransfer(
            0,
            new PublicKey(new Hash(complete.hashOfThePendingTransfer.get())),
            TestUtils.zac,
            BigInteger.valueOf(5),
            (short) 0,
            (short) 0,
            new Signature(),
            0,
            complete.hashOfThePendingTransfer);

    isValid = lisztManager1.addTransfer(unlock);
    Assert.assertTrue(isValid);

    for (int i = 0; i < 2; i++) {
      isValid =
          lisztManager1.addTransfer(
              TestUtils.createMockTransferFromAliceToBob(i, BigInteger.valueOf(0)));
      Assert.assertTrue(isValid);
    }

    Account zacAcc = lisztManager1.getAccount(TestUtils.zac);
    Assert.assertEquals(zacAcc.amount, BigInteger.valueOf(105));

    lockedAcc = lisztManager1.getAccount(rTransfer.hash.asHex);
    Assert.assertEquals(lockedAcc.amount, BigInteger.valueOf(0));
  }
}

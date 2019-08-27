package net.consensys.liszt.server;

import java.math.BigInteger;
import net.consensys.liszt.accountmanager.Account;
import net.consensys.liszt.core.common.RTransfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LisztManagerTest {

  private LisztManager lisztManager;

  @Before
  public void setUp() {
    short rid1 = 0;
    short rid2 = 1;

    lisztManager = new LisztManagerImp(rid1, rid2);
  }

  @Test
  public void validTransfersShouldUpdateAccountBalance() throws Exception {
    for (int i = 0; i < 10; i++) {
      boolean isValid =
          lisztManager.addTransfer(
              TestUtils.createMockTransferFromAliceToBob(i, BigInteger.valueOf(5)));
      Assert.assertTrue(isValid);
    }
    Account aliceAcc = lisztManager.getAccount(TestUtils.alice);
    Assert.assertEquals(aliceAcc.amount, BigInteger.valueOf(55));
    Account bobAcc = lisztManager.getAccount(TestUtils.bob);
    Assert.assertEquals(bobAcc.amount, BigInteger.valueOf(145));

    RTransfer rTransfer = TestUtils.createMockTransferFromAliceToBob(3, BigInteger.valueOf(5));
    long timeout = lisztManager.getLockDoneTimeout(rTransfer.hash);
    Assert.assertEquals(timeout, 0);
  }

  @Test
  public void illegalTransfersShouldBeRejected() {
    for (int i = 0; i < 10; i++) {
      boolean isValid =
          lisztManager.addTransfer(
              TestUtils.createMockTransferFromAliceToBob(i, BigInteger.valueOf(800)));
      Assert.assertTrue(isValid);
    }
    Account aliceAcc = lisztManager.getAccount(TestUtils.alice);
    Assert.assertEquals(aliceAcc.amount, BigInteger.valueOf(100));
  }

  @Test
  public void illegalTransfersShouldBeFilteredOut() {
    for (int i = 0; i < 21; i = i + 2) {
      lisztManager.addTransfer(
          TestUtils.createMockTransferFromAliceToBob(i, BigInteger.valueOf(800)));
      lisztManager.addTransfer(
          TestUtils.createMockTransferFromAliceToBob(i + 1, BigInteger.valueOf(5)));
    }
    Account aliceAcc = lisztManager.getAccount(TestUtils.alice);
    Assert.assertEquals(aliceAcc.amount, BigInteger.valueOf(55));
  }

  @Test
  public void crossRollupTransfersShouldBeLocked() throws Exception {
    for (int i = 0; i < 10; i++) {
      boolean isValid =
          lisztManager.addTransfer(
              TestUtils.createMockXTransferFromAliceToBob(i, BigInteger.valueOf(5)));
      Assert.assertTrue(isValid);
    }
    Account aliceAcc = lisztManager.getAccount(TestUtils.alice);
    Assert.assertEquals(aliceAcc.amount, BigInteger.valueOf(55));
    Account bobAcc = lisztManager.getAccount(TestUtils.bob);
    Assert.assertEquals(bobAcc.amount, BigInteger.valueOf(100));

    for (int i = 0; i < lisztManager.getLockAccounts().size(); i++) {
      Account acc = lisztManager.getLockAccounts().get(i);
      Assert.assertEquals(acc.amount, BigInteger.valueOf(5));
      RTransfer rTransfer = TestUtils.createMockXTransferFromAliceToBob(i, BigInteger.valueOf(5));
      Assert.assertEquals(acc.publicKey.hash, rTransfer.hash);
    }

    RTransfer rTransfer = TestUtils.createMockXTransferFromAliceToBob(3, BigInteger.valueOf(5));
    long timeout = lisztManager.getLockDoneTimeout(rTransfer.hash);
    Assert.assertEquals(timeout, 100);

    Account lockedAcc = lisztManager.getAccount(rTransfer.hash.asHex);
    Assert.assertEquals(lockedAcc.amount, BigInteger.valueOf(5));
  }

  @Test
  public void mixTransfers() throws Exception {
    for (int i = 0; i < 10; i++) {
      boolean isValid =
          lisztManager.addTransfer(
              TestUtils.createMockXTransferFromAliceToBob(i, BigInteger.valueOf(5)));
      Assert.assertTrue(isValid);
      isValid =
          lisztManager.addTransfer(
              TestUtils.createMockTransferFromAliceToBob(i, BigInteger.valueOf(5)));
      Assert.assertTrue(isValid);
    }

    Account aliceAcc = lisztManager.getAccount(TestUtils.alice);
    Assert.assertEquals(aliceAcc.amount, BigInteger.valueOf(10));
    Account bobAcc = lisztManager.getAccount(TestUtils.bob);
    Assert.assertEquals(bobAcc.amount, BigInteger.valueOf(145));

    RTransfer rTransfer = TestUtils.createMockXTransferFromAliceToBob(3, BigInteger.valueOf(5));
    long timeout = lisztManager.getLockDoneTimeout(rTransfer.hash);
    Assert.assertEquals(timeout, 100);
  }
}

package net.consensys.liszt.server;

import java.math.BigInteger;
import net.consensys.liszt.accountmanager.Account;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.PublicKey;
import net.consensys.liszt.core.crypto.Signature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LisztManagerTest {

  private PublicKey alice = new PublicKey("Alice");
  private PublicKey bob = new PublicKey("Bob");
  private LisztManager lisztManager;

  @Before
  public void setUp() {
    lisztManager = new LisztManagerImp();
  }

  @Test
  public void validTransfersShouldUpdateAccountBalance() {
    for (int i = 0; i < 10; i++) {
      boolean isValid =
          lisztManager.addTransfer(createMockTransferFromAliceToBob(i, BigInteger.valueOf(5)));
      Assert.assertTrue(isValid);
    }
    Account aliceAcc = lisztManager.getAccount(alice);
    Assert.assertEquals(aliceAcc.amount, BigInteger.valueOf(55));
    Account bobAcc = lisztManager.getAccount(bob);
    Assert.assertEquals(bobAcc.amount, BigInteger.valueOf(145));
  }

  @Test
  public void illegalTransfersShouldBeRejected() {
    for (int i = 0; i < 10; i++) {
      boolean isValid =
          lisztManager.addTransfer(createMockTransferFromAliceToBob(i, BigInteger.valueOf(800)));
      Assert.assertTrue(isValid);
    }
    Account aliceAcc = lisztManager.getAccount(alice);
    Assert.assertEquals(aliceAcc.amount, BigInteger.valueOf(100));
  }

  @Test
  public void illegalTransfersShouldBeFilteredOut() {
    for (int i = 0; i < 21; i = i + 2) {
      lisztManager.addTransfer(createMockTransferFromAliceToBob(i, BigInteger.valueOf(800)));
      lisztManager.addTransfer(createMockTransferFromAliceToBob(i + 1, BigInteger.valueOf(5)));
    }
    Account aliceAcc = lisztManager.getAccount(alice);
    Assert.assertEquals(aliceAcc.amount, BigInteger.valueOf(55));
  }

  @Test
  public void crossRollupTransfersShouldBeLocked() {
    for (int i = 0; i < 10; i++) {
      boolean isValid =
          lisztManager.addTransfer(createMockXTransferFromAliceToBob(i, BigInteger.valueOf(5)));
      Assert.assertTrue(isValid);
    }
    Account aliceAcc = lisztManager.getAccount(alice);
    Assert.assertEquals(aliceAcc.amount, BigInteger.valueOf(55));
    Account bobAcc = lisztManager.getAccount(bob);
    Assert.assertEquals(bobAcc.amount, BigInteger.valueOf(100));

    for (int i = 0; i < lisztManager.getLockAccounts().size(); i++) {
      Account acc = lisztManager.getLockAccounts().get(i);
      Assert.assertEquals(acc.amount, BigInteger.valueOf(5));
      RTransfer rTransfer = createMockXTransferFromAliceToBob(i, BigInteger.valueOf(5));
      Assert.assertEquals(acc.publicKey.owner, rTransfer.hash.asHex);
    }
  }

  public RTransfer createMockTransferFromAliceToBob(int i, BigInteger amount) {
    return new RTransfer(i, alice, bob, amount, 1, 1, new Signature());
  }

  public RTransfer createMockXTransferFromAliceToBob(int i, BigInteger amount) {
    return new RTransfer(i, alice, bob, amount, 0, 1, new Signature());
  }
}

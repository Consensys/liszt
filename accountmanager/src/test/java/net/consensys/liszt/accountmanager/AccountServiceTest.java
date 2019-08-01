package net.consensys.liszt.accountmanager;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.*;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.HashUtil;
import net.consensys.liszt.core.crypto.PublicKey;
import net.consensys.liszt.core.crypto.Signature;
import org.junit.Before;
import org.junit.Test;

public class AccountServiceTest {

  private static AccountService accountService;
  private static PublicKey alice = new PublicKey("Alice");
  private static PublicKey bob = new PublicKey("Bob");
  private static PublicKey zac = new PublicKey("Zac");
  private static PublicKey kate = new PublicKey("Kate");

  private static Hash initialRootHash;

  @Before
  public void setUp() {
    AccountsStateProvider accountsStateProvider = new AccountsStateProvider();
    Map<Hash, AccountsState> accountsState = accountsStateProvider.initialAccountsState;
    this.initialRootHash = accountsStateProvider.lastAcceptedRootHash;
    AccountRepository accountRepository = new AccountRepositoryImp(accountsState);
    this.accountService = new AccountServiceImp(accountRepository, initialRootHash);
  }

  @Test
  public void innerRollupTransfer() {
    List<RTransfer> transfers = innerRollupTransfers();
    List<RTransfer> invalidTransfers =
        accountService.updateIfAllTransfersValid(transfers, initialRootHash);
    assertTrue(invalidTransfers.isEmpty());
    Hash updatedRootHash = accountService.getLastAcceptedRootHash();

    BigInteger aliceAmount =
        accountService.getAccount(updatedRootHash, HashUtil.hash(alice.owner)).amount;
    BigInteger bobAmount =
        accountService.getAccount(updatedRootHash, HashUtil.hash(bob.owner)).amount;
    assertEquals(aliceAmount, BigInteger.valueOf(90));
    assertEquals(bobAmount, BigInteger.valueOf(110));

    // Test rollback
    BigInteger initialAliceAmount =
        accountService.getAccount(initialRootHash, HashUtil.hash(alice.owner)).amount;
    BigInteger initialBobAmount =
        accountService.getAccount(initialRootHash, HashUtil.hash(bob.owner)).amount;

    assertEquals(initialAliceAmount, BigInteger.valueOf(100));
    assertEquals(initialBobAmount, BigInteger.valueOf(100));
  }

  @Test
  public void invalidBalanceInnerRollupTransfer() {
    List<RTransfer> transfers = invalidBalanceInnerRollupTransfers();
    List<RTransfer> invalidTransfers =
        accountService.updateIfAllTransfersValid(transfers, initialRootHash);
    assertFalse(invalidTransfers.isEmpty());
    Hash updatedRootHash = accountService.getLastAcceptedRootHash();

    BigInteger aliceAmount =
        accountService.getAccount(updatedRootHash, HashUtil.hash(alice.owner)).amount;
    BigInteger bobAmount =
        accountService.getAccount(updatedRootHash, HashUtil.hash(bob.owner)).amount;

    assertEquals(aliceAmount, BigInteger.valueOf(100));
    assertEquals(bobAmount, BigInteger.valueOf(100));
  }

  @Test
  public void crossRollupTransferShouldBeLocked() {
    List<RTransfer> transfers = new ArrayList<>();
    RTransfer transfer1 =
        new RTransfer(0, alice, kate, BigInteger.valueOf(20), 1, 0, new Signature());
    RTransfer transfer2 =
        new RTransfer(0, zac, kate, BigInteger.valueOf(30), 1, 0, new Signature());

    transfers.add(transfer1);
    transfers.add(transfer2);

    accountService.updateIfAllTransfersValid(transfers, initialRootHash);
    Hash updatedRootHash = accountService.getLastAcceptedRootHash();
    BigInteger aliceAmount =
        accountService.getAccount(updatedRootHash, HashUtil.hash(alice.owner)).amount;
    BigInteger zacAmount =
        accountService.getAccount(updatedRootHash, HashUtil.hash(zac.owner)).amount;

    assertEquals(aliceAmount, BigInteger.valueOf(80));
    assertEquals(zacAmount, BigInteger.valueOf(70));

    Account transfer1Acc = accountService.getAccount(updatedRootHash, transfer1.hash);
    assertEquals(transfer1Acc.amount, BigInteger.valueOf(20));

    Account transfer2Acc = accountService.getAccount(updatedRootHash, transfer2.hash);
    assertEquals(transfer2Acc.amount, BigInteger.valueOf(30));
  }

  @Test
  public void checkBasicValidity() {
    List<RTransfer> invalidTransfers = invalidAccountInnerRollupTransfers();
    invalidTransfers.forEach(
        t -> {
          boolean isValid = accountService.checkBasicValidity(t, initialRootHash);
          assertFalse(isValid);
        });

    List<RTransfer> validTransfers = innerRollupTransfers();
    validTransfers.forEach(
        t -> {
          boolean isValid = accountService.checkBasicValidity(t, initialRootHash);
          assertTrue(isValid);
        });
  }

  private List<RTransfer> innerRollupTransfers() {
    List<RTransfer> transfers = new ArrayList<>();
    RTransfer transfer =
        new RTransfer(0, alice, bob, BigInteger.valueOf(10), 0, 0, new Signature());
    transfers.add(transfer);
    return transfers;
  }

  private List<RTransfer> invalidBalanceInnerRollupTransfers() {
    List<RTransfer> transfers = new ArrayList<>();
    RTransfer transfer =
        new RTransfer(0, alice, bob, BigInteger.valueOf(1000), 0, 0, new Signature());

    transfers.add(transfer);
    return transfers;
  }

  private List<RTransfer> invalidAccountInnerRollupTransfers() {
    List<RTransfer> transfers = new ArrayList<>();
    RTransfer transfer =
        new RTransfer(0, kate, bob, BigInteger.valueOf(1000), 0, 0, new Signature());

    transfers.add(transfer);
    return transfers;
  }

  private List<RTransfer> crossRollupTransfers() {
    List<RTransfer> transfers = new ArrayList<>();
    RTransfer transfer1 =
        new RTransfer(0, alice, kate, BigInteger.valueOf(20), 1, 0, new Signature());
    RTransfer transfer2 =
        new RTransfer(0, zac, kate, BigInteger.valueOf(30), 1, 0, new Signature());

    transfers.add(transfer1);
    transfers.add(transfer2);

    return transfers;
  }
}

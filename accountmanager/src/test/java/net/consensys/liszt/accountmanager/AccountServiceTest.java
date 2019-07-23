package net.consensys.liszt.accountmanager;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.*;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.PublicKey;
import net.consensys.liszt.core.crypto.Signature;
import org.junit.BeforeClass;
import org.junit.Test;

public class AccountServiceTest {

  private static Map<String, PublicKey> participants;
  private static AccountService accountService;
  private static PublicKey alice = new PublicKey("Alice");
  private static PublicKey bob = new PublicKey("Bob");
  private static Hash initialRootHash;

  @BeforeClass
  public static void setUp() {
    LinkedHashMap<PublicKey, Account> accounts = accounts();
    HashMap<Hash, LinkedHashMap<PublicKey, Account>> accountState = new HashMap<>();
    MerkleTree mt = new MerkleTree(accounts);
    initialRootHash = new Hash(); // mt.getRootHash();
    accountState.put(initialRootHash, accounts);
    accountService = new AccountServiceImp(accountState);
  }

  @Test
  public void innerRollupTransferTest() {
    List<RTransfer> transfers = innerRollupTransfers();
    accountService.updateIfAllTransfersValid(transfers, initialRootHash);
    Hash updatedRootHash = accountService.getLastAcceptedRootHash();
    BigInteger aliceAmount = accountService.getAccount(alice, updatedRootHash).amount;
    BigInteger bobAmount = accountService.getAccount(bob, updatedRootHash).amount;
    assertEquals(aliceAmount, BigInteger.valueOf(90));
    assertEquals(bobAmount, BigInteger.valueOf(110));

    BigInteger initialAliceAmount = accountService.getAccount(alice, initialRootHash).amount;
    BigInteger initialBobAmount = accountService.getAccount(bob, initialRootHash).amount;
    // assertEquals(initialAliceAmount, BigInteger.valueOf(100));
    // assertEquals(initialBobAmount, BigInteger.valueOf(100));
  }

  private List<RTransfer> innerRollupTransfers() {
    List<RTransfer> transfers = new ArrayList<>();
    RTransfer transfer =
        new RTransfer(0, alice, bob, BigInteger.valueOf(10), 0, 0, new Signature());
    transfers.add(transfer);
    return transfers;
  }

  private static LinkedHashMap<PublicKey, Account> accounts() {
    Account aliceAccount = new Account(alice, 0, BigInteger.valueOf(100), 0);
    Account bobAccount = new Account(bob, 0, BigInteger.valueOf(100), 0);
    LinkedHashMap<PublicKey, Account> accounts = new LinkedHashMap<>();
    accounts.put(alice, aliceAccount);
    accounts.put(bob, bobAccount);
    return accounts;
  }
}

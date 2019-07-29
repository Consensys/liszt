package net.consensys.liszt.server;

import java.math.BigInteger;
import java.util.*;
import net.consensys.liszt.accountmanager.Account;
import net.consensys.liszt.accountmanager.AccountService;
import net.consensys.liszt.accountmanager.AccountServiceImp;
import net.consensys.liszt.accountmanager.Accounts;
import net.consensys.liszt.blockchainmanager.BlockchainService;
import net.consensys.liszt.blockchainmanager.BlockchainServiceImp;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.PublicKey;
import net.consensys.liszt.core.crypto.Signature;
import net.consensys.liszt.provermanager.ProverService;
import net.consensys.liszt.provermanager.ProverServiceImp;
import net.consensys.liszt.transfermanager.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ControllerTest {

  private PublicKey alice = new PublicKey("Alice");
  private PublicKey bob = new PublicKey("Bob");
  private PublicKey zac = new PublicKey("Zac");
  private final int BATCH_SIZE = 3;
  private Controller controller;
  AccountService accountService;

  @Before
  public void setUp() {
    TransferService transferService = new TransferServiceImpl(BATCH_SIZE);
    LinkedHashMap<PublicKey, Account> accounts = createAccounts();
    Hash initialRootHash = Accounts.calculateNewRootHash(accounts);
    HashMap<Hash, LinkedHashMap<PublicKey, Account>> accountState = new HashMap<>();
    accountState.put(initialRootHash, accounts);

    accountService = new AccountServiceImp(accountState, initialRootHash);
    BatchService batchService = new BatchServiceImpl();
    ProverService proveService = new ProverServiceImp();
    BlockchainService blockchainService = new BlockchainServiceImp();

    controller =
        new ControllerImp(
            transferService,
            accountService,
            batchService,
            proveService,
            blockchainService,
            initialRootHash);
  }

  @Test
  public void validTransfersShouldUpdateAccountBalance() {
    for (int i = 0; i < 10; i++) {
      boolean isValid =
          controller.addTransfer(createMockTransferFromAliceToBob(i, BigInteger.valueOf(5)));
      Assert.assertTrue(isValid);
    }
    Account aliceAcc = controller.getAccount(alice);
    Assert.assertEquals(aliceAcc.amount, BigInteger.valueOf(55));
  }

  @Test
  public void illegalTransfersShouldBeRejected() {
    for (int i = 0; i < 10; i++) {
      boolean isValid =
          controller.addTransfer(createMockTransferFromAliceToBob(i, BigInteger.valueOf(800)));
      Assert.assertTrue(isValid);
    }
    Account aliceAcc = controller.getAccount(alice);
    Assert.assertEquals(aliceAcc.amount, BigInteger.valueOf(100));
  }

  @Test
  public void illegalTransfersShouldBeFilteredOut() {
    for (int i = 0; i < 21; i = i + 2) {
      controller.addTransfer(createMockTransferFromAliceToBob(i, BigInteger.valueOf(800)));
      controller.addTransfer(createMockTransferFromAliceToBob(i + 1, BigInteger.valueOf(5)));
    }
    Account aliceAcc = controller.getAccount(alice);
    Assert.assertEquals(aliceAcc.amount, BigInteger.valueOf(55));
  }

  private LinkedHashMap<PublicKey, Account> createAccounts() {
    List<PublicKey> publicKeys = Arrays.asList(new PublicKey[] {alice, bob});
    LinkedHashMap<PublicKey, Account> accounts = Accounts.accounts(publicKeys);
    return accounts;
  }

  public RTransfer createMockTransferFromAliceToBob(int i, BigInteger amount) {
    return new RTransfer(i, alice, bob, amount, 1, 1, new Signature());
  }
}

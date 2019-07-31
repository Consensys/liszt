package net.consensys.liszt.server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import net.consensys.liszt.accountmanager.Account;
import net.consensys.liszt.accountmanager.Accounts;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.PublicKey;

public class InitialConfiguration {
  public final int batchSize = 3;
  public final Hash initialRootHash;
  public final HashMap<Hash, LinkedHashMap<PublicKey, Account>> accountState;

  public InitialConfiguration() {
    LinkedHashMap<PublicKey, Account> accounts = createAccounts();
    initialRootHash = Accounts.calculateNewRootHash(accounts);
    accountState = new HashMap<>();
    accountState.put(initialRootHash, accounts);
  }

  private LinkedHashMap<PublicKey, Account> createAccounts() {
    PublicKey alice = new PublicKey("Alice");
    PublicKey bob = new PublicKey("Bob");
    PublicKey zac = new PublicKey("Zac");
    List<PublicKey> publicKeys = Arrays.asList(new PublicKey[] {alice, bob, zac});
    LinkedHashMap<PublicKey, Account> accounts = Accounts.accounts(publicKeys);
    return accounts;
  }
}

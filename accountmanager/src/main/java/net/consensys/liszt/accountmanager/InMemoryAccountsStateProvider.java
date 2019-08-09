package net.consensys.liszt.accountmanager;

import java.util.*;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.PublicKey;

public class InMemoryAccountsStateProvider implements AccountStateProvider {
  private final int batchSize = 3;
  private final Hash lastAcceptedRootHash;
  private final Map<Hash, AccountsState> initialAccountsState;

  public InMemoryAccountsStateProvider() {
    AccountsState accountsState = createAccountsState();
    lastAcceptedRootHash = Accounts.calculateNewRootHash(accountsState);
    initialAccountsState = new HashMap<>();
    initialAccountsState.put(lastAcceptedRootHash, accountsState);
  }

  private AccountsState createAccountsState() {
    PublicKey alice = new PublicKey("Alice");
    PublicKey bob = new PublicKey("Bob");
    PublicKey zac = new PublicKey("Zac");
    PublicKey carol = new PublicKey("Carol");
    PublicKey adam = new PublicKey("Adam");
    PublicKey greg = new PublicKey("Greg");

    List<PublicKey> publicKeys =
        Arrays.asList(new PublicKey[] {alice, bob, zac, carol, adam, greg});
    LinkedHashMap<Hash, Account> accounts = Accounts.accounts(publicKeys);
    return new AccountsState(accounts);
  }

  @Override
  public int batchSize() {
    return batchSize;
  }

  @Override
  public Map<Hash, AccountsState> initialAccountsState() {
    return initialAccountsState;
  }

  @Override
  public Hash lastAcceptedRootHash() {
    return lastAcceptedRootHash;
  }
}

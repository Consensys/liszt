package net.consensys.liszt.accountmanager;

import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.PublicKey;

import java.util.*;

public class AccountsStateProvider {
  public final int batchSize = 3;
  public final Hash lastAcceptedRootHash;
  public final Map<Hash, AccountsState> initialAccountsState;
  public AccountsStateProvider() {
    AccountsState accountsState = createAccountsState();
    lastAcceptedRootHash = Accounts.calculateNewRootHash(accountsState);
    initialAccountsState = new HashMap<>();
    initialAccountsState.put(lastAcceptedRootHash, accountsState);
  }

  private AccountsState createAccountsState() {
    PublicKey alice = new PublicKey("Alice");
    PublicKey bob = new PublicKey("Bob");
    PublicKey zac = new PublicKey("Zac");
    List<PublicKey> publicKeys = Arrays.asList(new PublicKey[] {alice, bob, zac});
    LinkedHashMap<Hash, Account> accounts = Accounts.accounts(publicKeys);
    return new AccountsState(accounts);
  }
}
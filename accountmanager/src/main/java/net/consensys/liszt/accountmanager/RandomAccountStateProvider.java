package net.consensys.liszt.accountmanager;

import java.nio.charset.Charset;
import java.util.*;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.PublicKey;

public class RandomAccountStateProvider implements AccountStateProvider {

  private final Random generator;
  private final int batchSize = 3;
  private final int nbOfAccounts = 6;
  private final Hash lastAcceptedRootHash;
  private final Map<Hash, AccountsState> initialAccountsState;

  public RandomAccountStateProvider(int seed) {
    this.generator = new Random(seed);
    AccountsState accountsState = loadAccountsState();
    lastAcceptedRootHash = Accounts.calculateNewRootHash(accountsState);
    initialAccountsState = new HashMap<>();
    initialAccountsState.put(lastAcceptedRootHash, accountsState);
  }

  private AccountsState loadAccountsState() {
    List<PublicKey> keys = new ArrayList<>();
    keys.add(new PublicKey("intermediary"));
    for (int i = 0; i < nbOfAccounts; i++) {
      byte[] bytes = new byte[7];
      generator.nextBytes(bytes);
      String generatedString = new String(bytes, Charset.forName("UTF-8"));
      PublicKey key = new PublicKey(generatedString);
      keys.add(key);
    }
    LinkedHashMap<Hash, Account> accounts = Accounts.accounts(keys);
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

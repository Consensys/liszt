package net.consensys.liszt.accountmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.consensys.liszt.core.crypto.Hash;

public class AccountRepositoryImp implements AccountRepository {
  private final Map<Hash, AccountsState> accountState;

  public AccountRepositoryImp(Map<Hash, AccountsState> accountState) {
    this.accountState = accountState;
  }

  @Override
  public boolean exists(Hash rootHash, Hash owner) {
    return this.get(rootHash, owner) != null;
  }

  @Override
  public Account get(Hash rootHash, Hash owner) {
    return accountState.get(rootHash).get(owner);
  }

  @Override
  public List<Account> getAccounts(Hash rootHash) {
    return new ArrayList<>(accountState.get(rootHash).accountsState.values());
  }

  @Override
  public AccountsState cloneAccountsState(Hash rootHash) {
    return accountState.get(rootHash).clone();
  }

  @Override
  public void saveNewAccountsState(Hash rootHash, AccountsState accountsState) {
    this.accountState.put(rootHash, accountsState);
  }
}

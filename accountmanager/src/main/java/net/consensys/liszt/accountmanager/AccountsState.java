package net.consensys.liszt.accountmanager;

import java.util.LinkedHashMap;
import net.consensys.liszt.core.crypto.Hash;

public class AccountsState {
  protected final LinkedHashMap<Hash, Account> accountsState;

  public AccountsState(LinkedHashMap<Hash, Account> accountsState) {
    this.accountsState = accountsState;
  }

  public Account get(Hash owner) {
    return accountsState.get(owner);
  }

  public AccountsState clone() {
    return new AccountsState(new LinkedHashMap<>(accountsState));
  }

  public void put(Hash owner, Account acc) {
    accountsState.put(owner, acc);
  }
}

package net.consensys.liszt.accountmanager;

import net.consensys.liszt.core.crypto.Hash;

public interface AccountRepository {

  /**
   * @param rootHash rollup's merkle root
   * @param owner for inner rollp transactions is the hash of owners public key, for cross rollup
   *     transfers is the hash of the transfer. Checks existence of an account in the rollup
   */
  boolean exists(Hash rootHash, Hash owner);

  /**
   * @param rootHash
   * @return copy of the account state
   */
  AccountsState cloneAccountsState(Hash rootHash);

  /**
   * @param rootHash
   * @param owner
   * @return account
   */
  Account get(Hash rootHash, Hash owner);

  /** saves accounts state in the repository */
  void saveNewAccountsState(Hash rootHash, AccountsState accountsState);
}

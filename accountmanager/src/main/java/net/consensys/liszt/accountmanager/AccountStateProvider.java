package net.consensys.liszt.accountmanager;

import java.util.Map;
import net.consensys.liszt.core.crypto.Hash;

public interface AccountStateProvider {
  int batchSize();

  Map<Hash, AccountsState> initialAccountsState();

  Hash lastAcceptedRootHash();
}

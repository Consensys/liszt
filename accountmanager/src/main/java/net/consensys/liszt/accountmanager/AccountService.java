package net.consensys.liszt.accountmanager;

import java.util.List;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;

public interface AccountService {

  /** @return true if the transfer is valid, eg. the account exists. */
  boolean checkBasicValidity(RTransfer transfer);

  /** Update the state for all these transfers operations. Return the new root hash. */
  Hash update(List<RTransfer> transfers, Hash fatherRootHash);

  AccountState getAccountStatus(byte[] account, Hash rootHash);
}

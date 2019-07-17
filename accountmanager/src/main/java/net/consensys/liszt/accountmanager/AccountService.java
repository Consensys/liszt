package net.consensys.liszt.accountmanager;

import net.consensys.liszt.core.common.RTransfer;

import java.util.List;

public interface AccountService {

  /** @return true if the transfer is valid, eg. the account exists. */
  boolean checkBasicValidity(RTransfer transfer);

  /** Update the state for all these transfers operations. Return the new root hash. */
  byte[] update(List<RTransfer> transfers, byte[] fatherRootHash);

  AccountState getAccountStatus(byte[] account, byte[] rootHash);
}

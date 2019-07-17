package net.consensys.liszt.accountmanager;

import java.util.List;
import net.consensys.liszt.transactionmanager.RTransfer;

public interface AccountService {

  /** @return true if the transfer is valid, eg. the account exists. */
  boolean checkBasicValidity(RTransfer transfer);

  /** Update the state for all these transfers operations. Return the new root hash. */
  byte[] update(List<RTransfer> transfers, byte[] fatherRootHash);

  AccountState getAccountStatus(byte[] account, byte[] rootHash);
}

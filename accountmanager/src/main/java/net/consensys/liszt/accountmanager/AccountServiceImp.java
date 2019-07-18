package net.consensys.liszt.accountmanager;

import java.util.List;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;

public class AccountServiceImp implements AccountService {
  @Override
  public boolean checkBasicValidity(RTransfer transfer) {
    return transfer.isSigned();
  }

  @Override
  public Hash update(List<RTransfer> transfers, Hash fatherRootHash) {
    return null;
  }

  @Override
  public AccountState getAccountStatus(byte[] account, Hash rootHash) {
    return null;
  }
}

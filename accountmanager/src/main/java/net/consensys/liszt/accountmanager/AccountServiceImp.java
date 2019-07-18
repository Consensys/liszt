package net.consensys.liszt.accountmanager;

import net.consensys.liszt.core.common.RTransfer;

import java.util.List;

public class AccountServiceImp implements AccountService {
    @Override
    public boolean checkBasicValidity(RTransfer transfer) {
        return transfer.isSigned();
    }

    @Override
    public byte[] update(List<RTransfer> transfers, byte[] fatherRootHash) {
        return new byte[0];
    }

    @Override
    public AccountState getAccountStatus(byte[] account, byte[] rootHash) {
        return null;
    }
}

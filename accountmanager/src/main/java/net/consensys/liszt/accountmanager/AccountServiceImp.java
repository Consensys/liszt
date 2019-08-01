package net.consensys.liszt.accountmanager;

import java.math.BigInteger;
import java.util.*;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.HashUtil;

public class AccountServiceImp implements AccountService {

  private final AccountRepository accountRepository;
  private Hash lastAcceptedRootHash;

  public AccountServiceImp(AccountRepository accountRepository, Hash initialRootHash) {
    this.accountRepository = accountRepository;
    this.lastAcceptedRootHash = initialRootHash;
  }

  @Override
  public boolean checkBasicValidity(RTransfer transfer, Hash fatherRootHash) {
    // For inner rollup transfers, transfer is valid if:
    // 1) Signature valid
    // 2) "from", and "to" accounts exist is in the rolluo.
    // TODO conditions for cross rollup transfer

    return transfer.isSigned()
        && accountRepository.exists(fatherRootHash, HashUtil.hash(transfer.to.owner))
        && accountRepository.exists(fatherRootHash, HashUtil.hash(transfer.from.owner));
  }

  @Override
  public List<RTransfer> updateIfAllTransfersValid(List<RTransfer> transfers, Hash fatherRootHash) {
    AccountsState tmpAccounts = accountRepository.cloneAccountsState(fatherRootHash);
    List<RTransfer> notAcceptedTransfers = new ArrayList<>();
    for (RTransfer transfer : transfers) {
      boolean accepted;
      if (transfer.rIdFrom == transfer.rIdTo) {
        accepted = innerRollupTransfer(transfer, tmpAccounts);
      } else {
        accepted = crossRollupTransfer(transfer, tmpAccounts);
      }
      if (!accepted) {
        notAcceptedTransfers.add(transfer);
      }
    }

    if (notAcceptedTransfers.isEmpty()) {
      Hash newRootHash = Accounts.calculateNewRootHash(tmpAccounts);
      this.lastAcceptedRootHash = newRootHash;
      accountRepository.saveNewAccountsState(newRootHash, tmpAccounts);
    }
    return notAcceptedTransfers;
  }

  @Override
  public Hash getLastAcceptedRootHash() {
    return lastAcceptedRootHash;
  }

  @Override
  public Account getAccount(Hash rootHash, Hash owner) {
    return accountRepository.get(rootHash, owner);
  }

  private boolean innerRollupTransfer(RTransfer transfer, AccountsState tmpAccounts) {
    Account fromAcc = tmpAccounts.get(HashUtil.hash(transfer.from.owner));

    BigInteger newFromAccBalance = fromAcc.amount.subtract(transfer.amount);
    if (newFromAccBalance.compareTo(BigInteger.ZERO) == -1) {
      return false;
    }
    Account toAcc = tmpAccounts.get(HashUtil.hash(transfer.to.owner));
    BigInteger newToAccBalance = toAcc.amount.add(transfer.amount);
    Account newFromAcc = Accounts.updateAccount(fromAcc, newFromAccBalance);
    // TODO if newFromAcc is Lock account then check if transfer can be submitted (in the
    // controller)
    Account newToAcc = Accounts.updateAccount(toAcc, newToAccBalance);
    tmpAccounts.put(HashUtil.hash(newFromAcc.publicKey.owner), newFromAcc);
    tmpAccounts.put(HashUtil.hash(newToAcc.publicKey.owner), newToAcc);
    return true;
  }

  private boolean crossRollupTransfer(RTransfer transfer, AccountsState tmpAccounts) {
    Account fromAcc = tmpAccounts.get(HashUtil.hash(transfer.from.owner));
    BigInteger newFromAccBalance = fromAcc.amount.subtract(transfer.amount);
    if (newFromAccBalance.compareTo(BigInteger.ZERO) == -1) {
      return false;
    }
    Account newFromAcc = Accounts.updateAccount(fromAcc, newFromAccBalance);
    Account lockedAcc = Accounts.createLockAccount(Accounts.Lock, 0, transfer.amount, 0);
    tmpAccounts.put(HashUtil.hash(newFromAcc.publicKey.owner), newFromAcc);
    tmpAccounts.put(transfer.hash, lockedAcc);
    return true;
  }
}

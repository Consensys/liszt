package net.consensys.liszt.accountmanager;

import java.math.BigInteger;
import java.util.*;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.PublicKey;

public class AccountServiceImp implements AccountService {

  private final HashMap<Hash, LinkedHashMap<PublicKey, Account>> accountState;
  private Hash lastAcceptedRootHash;

  public AccountServiceImp(
      HashMap<Hash, LinkedHashMap<PublicKey, Account>> accountState, Hash initialRootHash) {
    this.accountState = accountState;
    this.lastAcceptedRootHash = initialRootHash;
  }

  @Override
  public boolean checkBasicValidity(RTransfer transfer, Hash fatherRootHash) {
    // For inner rollup transfers, transfer is valid if:
    // 1) Signature valid
    // 2) "from", and "to" accounts exist is in the rolluo.
    // TODO conditions for cross rollup transfer

    return transfer.isSigned()
        && accountState.get(fatherRootHash).get(transfer.to) != null
        && accountState.get(fatherRootHash).get(transfer.from) != null;
  }

  @Override
  public List<RTransfer> updateIfAllTransfersValid(List<RTransfer> transfers, Hash fatherRootHash) {
    LinkedHashMap<PublicKey, Account> tmpAccounts =
        new LinkedHashMap<>(accountState.get(fatherRootHash));
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
      accountState.put(newRootHash, tmpAccounts);
      this.lastAcceptedRootHash = newRootHash;
    }
    return notAcceptedTransfers;
  }

  @Override
  public Hash getLastAcceptedRootHash() {
    return lastAcceptedRootHash;
  }

  @Override
  public Account getAccount(PublicKey owner, Hash rootHash) {
    return accountState.get(rootHash).get(owner);
  }

  private boolean innerRollupTransfer(
      RTransfer transfer, LinkedHashMap<PublicKey, Account> tmpAccounts) {
    Account fromAcc = tmpAccounts.get(transfer.from);

    BigInteger newFromAccBalance = fromAcc.amount.subtract(transfer.amount);
    if (newFromAccBalance.compareTo(BigInteger.ZERO) == -1) {
      return false;
    }
    Account toAcc = tmpAccounts.get(transfer.to);
    BigInteger newToAccBalance = toAcc.amount.add(transfer.amount);
    Account newFromAcc = Accounts.updateAccount(fromAcc, newFromAccBalance);
    Account newToAcc = Accounts.updateAccount(toAcc, newToAccBalance);
    tmpAccounts.put(newFromAcc.publicKey, newFromAcc);
    tmpAccounts.put(newToAcc.publicKey, newToAcc);
    return true;
  }

  private boolean crossRollupTransfer(
      RTransfer transfer, LinkedHashMap<PublicKey, Account> tmpAccounts) {
    return true;
  }
}

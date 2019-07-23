package net.consensys.liszt.accountmanager;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
      Hash newRootHash = calculateNewRootHash(tmpAccounts);
      accountState.put(newRootHash, tmpAccounts);
      lastAcceptedRootHash = newRootHash;
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
    Account newFromAcc =
        new Account(fromAcc.publicKey, fromAcc.id, newFromAccBalance, fromAcc.nonce);
    Account newToAcc = new Account(toAcc.publicKey, toAcc.id, newToAccBalance, toAcc.nonce);
    tmpAccounts.put(newFromAcc.publicKey, newFromAcc);
    tmpAccounts.put(newToAcc.publicKey, newToAcc);
    return true;
  }

  private boolean crossRollupTransfer(
      RTransfer transfer, LinkedHashMap<PublicKey, Account> tmpAccounts) {
    return true;
  }

  private Hash calculateNewRootHash(LinkedHashMap<PublicKey, Account> tmpAccounts) {
    return new Hash();
  }
}

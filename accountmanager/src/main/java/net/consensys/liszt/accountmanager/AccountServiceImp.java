package net.consensys.liszt.accountmanager;

import java.math.BigInteger;
import java.util.*;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.PublicKey;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class AccountServiceImp implements AccountService {

  private final AccountRepository accountRepository;
  private Hash lastAcceptedRootHash;
  private List<Hash> lockedTransfers;
  private static final Logger logger = LogManager.getLogger("Liszt");

  public AccountServiceImp(AccountRepository accountRepository, Hash initialRootHash) {
    this.accountRepository = accountRepository;
    this.lastAcceptedRootHash = initialRootHash;
    this.lockedTransfers = new ArrayList<>();
  }

  @Override
  public boolean checkBasicValidity(RTransfer transfer, Hash fatherRootHash) {
    // inner rollup transfers, transfer is valid if:
    // 1) Signature valid
    // 2) "from", and "to" accounts exist is in the rolluo.
    // TODO conditions for cross rollup transfer

    boolean idSigned = transfer.isSigned();
    boolean senderExist = accountRepository.exists(fatherRootHash, transfer.from.hash);
    boolean recipientExist = true;
    if (transfer.from == transfer.to) {
      recipientExist = accountRepository.exists(fatherRootHash, transfer.to.hash);
    }

    boolean ok = idSigned && senderExist && recipientExist;
    if (!ok) {
      logger.info(
          "Transfer is invalid "
              + transfer.hash.asHex
              + ", isSigned "
              + idSigned
              + ", senderExist "
              + senderExist
              + ", from "
              + transfer.from
              + ", recipientExist "
              + recipientExist
              + ", to "
              + transfer.to);
      // accountRepository.get(fatherRootHash)
    }
    return ok;
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
    Account fromAcc = tmpAccounts.get(transfer.from.hash);

    BigInteger newFromAccBalance = fromAcc.amount.subtract(transfer.amount);
    if (newFromAccBalance.compareTo(BigInteger.ZERO) == -1) {
      logger.info(
          "Insufficient balance for transfer "
              + transfer.hash.asHex.substring(0, 10)
              + " balance: "
              + fromAcc.amount
              + "transfer amount: "
              + transfer.amount);

      return false;
    }
    Account toAcc = tmpAccounts.get(transfer.to.hash);
    BigInteger newToAccBalance = toAcc.amount.add(transfer.amount);
    Account newFromAcc = Accounts.updateAccount(fromAcc, newFromAccBalance);
    Account newToAcc = Accounts.updateAccount(toAcc, newToAccBalance);
    tmpAccounts.put(newFromAcc.publicKey.hash, newFromAcc);
    tmpAccounts.put(newToAcc.publicKey.hash, newToAcc);
    logger.info(
        "Accounts updated for transfer "
            + fromAcc.publicKey.hash.asHex.substring(0, 10)
            + "..."
            + " -> "
            + toAcc.publicKey.hash.asHex.substring(0, 10)
            + "..."
            + " amount "
            + transfer.amount);
    return true;
  }

  private boolean crossRollupTransfer(RTransfer transfer, AccountsState tmpAccounts) {
    Account fromAcc = tmpAccounts.get(transfer.from.hash);
    BigInteger newFromAccBalance = fromAcc.amount.subtract(transfer.amount);
    if (newFromAccBalance.compareTo(BigInteger.ZERO) == -1) {
      return false;
    }
    Account newFromAcc = Accounts.updateAccount(fromAcc, newFromAccBalance);
    Account lockedAcc =
        Accounts.createLockAccount(new PublicKey(transfer.hash), 0, transfer.amount, 0);
    tmpAccounts.put(newFromAcc.publicKey.hash, newFromAcc);
    tmpAccounts.put(transfer.hash, lockedAcc);
    lockedTransfers.add(transfer.hash);
    logger.info(
        "X rollup transfer locked " + lockedAcc.publicKey.hash.asHex.substring(0, 10) + "...");
    return true;
  }

  public List<Account> getLockAccounts(Hash rootHash) {
    List<Account> accs = new ArrayList<>();
    lockedTransfers.forEach(h -> accs.add(getAccount(rootHash, h)));
    return accs;
  }

  public List<Account> getAccounts(Hash rootHash) {
    return accountRepository.getAccounts(rootHash);
  }
}

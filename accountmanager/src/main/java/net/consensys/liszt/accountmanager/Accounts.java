package net.consensys.liszt.accountmanager;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.HashUtil;
import net.consensys.liszt.core.crypto.MerkleTree;
import net.consensys.liszt.core.crypto.PublicKey;

public class Accounts {

  public static final PublicKey Lock = new PublicKey("Locked");

  public static Account createAccount(PublicKey publicKey, int id, BigInteger amount, int nonce) {
    Hash hash =
        HashUtil.hash(
            publicKey.toString(), String.valueOf(id), amount.toString(), String.valueOf(nonce));
    return new Account(publicKey, id, amount, nonce, hash, false);
  }

  public static Account createLockAccount(PublicKey publicKey, int id, BigInteger amount, int nonce) {
    Hash hash =
            HashUtil.hash(
                    publicKey.toString(), String.valueOf(id), amount.toString(), String.valueOf(nonce));
    return new Account(publicKey, id, amount, nonce, hash, true);
  }

  protected static Account updateAccount(Account account, BigInteger amount) {
    return createAccount(account.publicKey, account.id, amount, account.nonce + 1);
  }

  /** @return Root hash of the account merkle tree. */
  public static Hash calculateNewRootHash(LinkedHashMap<Hash, Account> tmpAccounts) {
    ArrayList<Hash> hashList = new ArrayList<Hash>();
    for (Map.Entry<Hash, Account> keyAcc : tmpAccounts.entrySet()) {
      hashList.add(keyAcc.getValue().hash);
    }
    MerkleTree mt = new MerkleTree(hashList);
    return mt.getRootHash();
  }

  /** @return Root hash of the account merkle tree. */
  public static Hash calculateNewRootHash(AccountsState tmpAccounts) {
    ArrayList<Hash> hashList = new ArrayList<Hash>();
    for (Map.Entry<Hash, Account> keyAcc : tmpAccounts.accountsState.entrySet()) {
      hashList.add(keyAcc.getValue().hash);
    }
    MerkleTree mt = new MerkleTree(hashList);
    return mt.getRootHash();
  }

  public static LinkedHashMap<Hash, Account> accounts(List<PublicKey> publicKeys) {
    LinkedHashMap<Hash, Account> accounts = new LinkedHashMap<>();
    for (PublicKey pk : publicKeys) {
      Account account = Accounts.createAccount(pk, 0, BigInteger.valueOf(100), 0);
      accounts.put(HashUtil.hash(pk.owner), account);
    }
    return accounts;
  }
}

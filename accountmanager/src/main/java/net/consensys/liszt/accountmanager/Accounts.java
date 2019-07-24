package net.consensys.liszt.accountmanager;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.HashUtil;
import net.consensys.liszt.core.crypto.MerkleTree;
import net.consensys.liszt.core.crypto.PublicKey;

public class Accounts {

  public static Account createAccount(PublicKey publicKey, int id, BigInteger amount, int nonce) {
    Hash hash =
        HashUtil.hash(
            publicKey.toString(), String.valueOf(id), amount.toString(), String.valueOf(nonce));
    return new Account(publicKey, id, amount, nonce, hash);
  }

  protected static Account updateAccount(Account account, BigInteger amount) {
    return createAccount(account.publicKey, account.id, amount, account.nonce + 1);
  }

  /** @return Root hash of the account merkle tree. */
  public static Hash calculateNewRootHash(LinkedHashMap<PublicKey, Account> tmpAccounts) {
    ArrayList<Hash> hashList = new ArrayList<Hash>();
    for (Map.Entry<PublicKey, Account> keyAcc : tmpAccounts.entrySet()) {
      hashList.add(keyAcc.getValue().hash);
    }
    MerkleTree mt = new MerkleTree(hashList);
    return mt.getRootHash();
  }
}

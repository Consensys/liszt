package net.consensys.liszt.accountmanager;

import java.util.LinkedHashMap;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.PublicKey;

public class MerkleTree {

  public MerkleTree(LinkedHashMap<PublicKey, Account> accounts) {}

  public Hash getRootHash() {
    return new Hash();
  }
}

package net.consensys.liszt.core.crypto;

import java.util.ArrayList;

public class MerkleTree {
  private final Hash rootHash;

  public MerkleTree(ArrayList<Hash> hashList) {
    rootHash = calculateRootHash(new ArrayList<>(hashList));
  }

  private Hash calculateRootHash(ArrayList<Hash> hashList) {
    if (hashList.size() == 0) {
      return HashUtil.ZERO_HASH;
    }
    if (hashList.size() % 2 != 0) {
      hashList.add(HashUtil.ZERO_HASH);
    }

    while (hashList.size() > 1) {
      Hash first = hashList.remove(0);
      Hash second = hashList.remove(0);
      Hash combined = HashUtil.combine(first, second);
      hashList.add(combined);
    }
    Hash root = hashList.get(0);
    return root;
  }

  public Hash getRootHash() {
    return rootHash;
  }
}

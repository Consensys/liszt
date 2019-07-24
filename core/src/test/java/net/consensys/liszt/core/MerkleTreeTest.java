package net.consensys.liszt.core;

import java.util.ArrayList;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.HashUtil;
import net.consensys.liszt.core.crypto.MerkleTree;
import org.junit.Assert;
import org.junit.Test;

public class MerkleTreeTest {

  @Test
  public void calculateRootHash() {
    ArrayList<Hash> hashList = new ArrayList<>();
    Hash hashA = HashUtil.hash("A");
    Hash hashB = HashUtil.hash("B");
    Hash hashC = HashUtil.hash("C");
    hashList.add(hashA);
    hashList.add(hashB);
    hashList.add(hashC);

    MerkleTree mt = new MerkleTree(hashList);
    Assert.assertEquals(3, hashList.size());
    Hash left = HashUtil.combine(hashA, hashB);
    Hash right = HashUtil.combine(hashC, HashUtil.ZERO_HASH);
    Hash combined = HashUtil.combine(left, right);
    Hash rootHash = mt.getRootHash();
    Assert.assertEquals(combined, rootHash);
  }

  @Test
  public void calculateRootHashForEmptyList() {
    ArrayList<Hash> hashList = new ArrayList<>();
    MerkleTree mt = new MerkleTree(hashList);
    Hash rootHash = mt.getRootHash();
    Assert.assertEquals(rootHash, HashUtil.ZERO_HASH);
  }
}

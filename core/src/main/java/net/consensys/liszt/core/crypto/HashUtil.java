package net.consensys.liszt.core.crypto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
  public static Hash ZERO_HASH = hash("0");

  private static MessageDigest messageDigest() {
    MessageDigest digest;
    try {
      /* TODO sha-256 is temporary  solution for the POC,
       *  we need to find and implement zkp friendly hash function */

      digest = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    return digest;
  }

  public static Hash hash(String... fields) {
    MessageDigest md = messageDigest();
    for (String s : fields) {
      md.update(s.getBytes(StandardCharsets.UTF_8));
    }
    return new Hash(md.digest());
  }

  public static Hash fromHex(String hex) {
    return new Hash(hex);
  }

  /**
   * Concatenate input hashes and return hash of the concatenation.
   *
   * @param hash1
   * @param hash2
   * @return Hash of both inputs
   */
  public static Hash combine(Hash hash1, Hash hash2) {
    MessageDigest md = messageDigest();
    md.update(hash1.hash);
    md.update(hash2.hash);
    return new Hash(md.digest());
  }
}

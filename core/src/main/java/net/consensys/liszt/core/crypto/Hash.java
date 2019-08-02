package net.consensys.liszt.core.crypto;

import com.google.common.io.BaseEncoding;
import java.util.Arrays;

public class Hash {

  protected final byte[] hash;
  public final String asHex;

  protected Hash(byte[] hash) {
    this.hash = hash;
    asHex = BaseEncoding.base16().encode(hash);
  }

  protected Hash(String hex) {
    this.hash = BaseEncoding.base16().decode(hex);
    asHex = hex;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Hash hash1 = (Hash) o;
    return Arrays.equals(hash, hash1.hash);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(hash);
  }
}

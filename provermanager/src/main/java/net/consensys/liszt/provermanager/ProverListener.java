package net.consensys.liszt.provermanager;

import net.consensys.liszt.core.crypto.Proof;

public interface ProverListener {
  void onNewProof(Proof proof);
}

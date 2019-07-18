package net.consensys.liszt.provermanager;

import net.consensys.liszt.core.common.Batch;

public interface ProverService {
  /** @param batch to prove starts prover for a givent batch */
  void proveBatch(Batch batch);
}
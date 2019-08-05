package net.consensys.liszt.provermanager;

import net.consensys.liszt.core.common.Batch;
import net.consensys.liszt.core.crypto.Proof;

public class ProverServiceImp implements ProverService {

  private ProverListener listener;

  @Override
  public void proveBatch(Batch batch) {
    Proof proof = new Proof(batch.rootHash);
    listener.onNewProof(proof);
  }

  @Override
  public void registerListener(ProverListener listener) {
    this.listener = listener;
  }
}

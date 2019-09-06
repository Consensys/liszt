package net.consensys.liszt.provermanager;

import java.util.concurrent.atomic.AtomicInteger;
import net.consensys.liszt.core.common.Batch;
import net.consensys.liszt.core.crypto.Proof;

public class ProverServiceImp implements ProverService {

  private ProverListener listener;

  private final AtomicInteger counter = new AtomicInteger(0);

  @Override
  public void proveBatch(Batch batch) {
    /*new Thread(
        () -> {
          try {
            Thread.sleep(10000);
            submitProof(batch);
          } catch (Exception e) {
            e.printStackTrace();
          }
        })
    .start();*/

    submitProof(batch);
  }

  private void submitProof(Batch batch) {
    int proofId = counter.addAndGet(1);
    Proof proof = new Proof(batch.rootHash, proofId);
    listener.onNewProof(proof);
  }

  @Override
  public void registerListener(ProverListener listener) {
    this.listener = listener;
  }
}

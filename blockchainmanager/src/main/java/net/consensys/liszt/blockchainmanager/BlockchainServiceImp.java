package net.consensys.liszt.blockchainmanager;

import net.consensys.liszt.blockchainmanager.contract.Liszt;
import net.consensys.liszt.blockchainmanager.ganache.GanacheController;
import net.consensys.liszt.core.common.Batch;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.Proof;

public class BlockchainServiceImp implements BlockchainService {

  private Controller controller;
  private Liszt liszt;

  @Override
  public void startLocalNode() throws Exception {
    if (controller == null) {
      this.controller = new GanacheController(10, 100);
    }
  }

  @Override
  public void deployContract() throws Exception {
    Deployer deployer =
        new LisztDeployer(controller.accounts().get(0).getPrivateKey(), controller.provider());

    this.liszt = new Liszt(deployer.deploySmartContract());
  }

  @Override
  public void submit(Batch batch, Proof proof) throws Exception {
    for (RTransfer t : batch.transfers) {
      if (t.rIdFrom != t.rIdTo) {
        //   XTransfer xTransfer = new XTransfer(t.from.owner, t.to.owner, t.amount, t.rIdFrom,
        // t.rIdTo, t.timeout);
        liszt.updateLockDone(t);
      }
    }
  }

  @Override
  public byte[] checkInclusion(Hash rootHash) {
    return new byte[0];
  }

  @Override
  public long getLockedDone(int rollupId, Hash txHash) throws Exception {
    return liszt.lockDoneTimeout(rollupId, txHash);
  }
}

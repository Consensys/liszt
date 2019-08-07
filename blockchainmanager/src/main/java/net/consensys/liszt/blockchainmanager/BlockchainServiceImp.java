package net.consensys.liszt.blockchainmanager;

import net.consensys.liszt.blockchainmanager.contract.Liszt;
import net.consensys.liszt.blockchainmanager.contract.LisztContract;
import net.consensys.liszt.blockchainmanager.ganache.GanacheController;
import net.consensys.liszt.core.common.Batch;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.Proof;

public class BlockchainServiceImp implements BlockchainService {

  private Liszt liszt;

  @Override
  public void startLocalNode() throws Exception {
    Controller controller = new GanacheController(10, 100);
    Deployer deployer = new LisztDeployer(controller.provider());

    if (!controller.isLocalNodeStarted()) {
      controller.start();

      LisztContract lisztContract =
          deployer.deploySmartContract(controller.accounts().get(0).getPrivateKey());

      this.liszt = new Liszt(lisztContract);

      controller.saveContractAddress(lisztContract.getContractAddress());

    } else {

      String addr = controller.getContractAddress();
      this.liszt =
          new Liszt(deployer.loadSmartContract(controller.accounts().get(0).getPrivateKey(), addr));
    }
  }

  @Override
  public void submit(Batch batch, Proof proof) throws Exception {
    for (RTransfer t : batch.transfers) {
      if (t.rIdFrom != t.rIdTo) {
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

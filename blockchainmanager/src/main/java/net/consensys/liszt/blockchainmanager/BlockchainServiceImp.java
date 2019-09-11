package net.consensys.liszt.blockchainmanager;

import java.math.BigInteger;
import net.consensys.liszt.blockchainmanager.contract.Liszt;
import net.consensys.liszt.blockchainmanager.contract.LisztContract;
import net.consensys.liszt.blockchainmanager.contract.TransferDone;
import net.consensys.liszt.blockchainmanager.ganache.GanacheController;
import net.consensys.liszt.core.common.Batch;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.Proof;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class BlockchainServiceImp implements BlockchainService {

  private Liszt liszt;
  private static final Logger logger = LogManager.getLogger("Liszt");
  private Deployer deployer;

  @Override
  public void startLocalNode() throws Exception {
    Controller controller = new GanacheController(10, 100);
    this.deployer = new LisztDeployer(controller.provider());

    if (!controller.isLocalNodeStarted()) {
      controller.start();

      LisztContract lisztContract =
          deployer.deploySmartContract(controller.accounts().get(0).getPrivateKey());

      this.liszt = new Liszt(lisztContract);
      String contractAddr = lisztContract.getContractAddress();
      logger.info("Deploying smart contract " + contractAddr);

      controller.saveContractAddress(lisztContract.getContractAddress());

    } else {

      String contractAddr = controller.getContractAddress();
      logger.info("Loading smart contract " + contractAddr);

      this.liszt =
          new Liszt(
              deployer.loadSmartContract(
                  controller.accounts().get(0).getPrivateKey(), contractAddr));
    }
  }

  @Override
  public void submit(Batch batch, Proof proof) throws Exception {
    liszt.update(batch.transfers, batch.rollupId, batch.rootHash, null);
   }

  @Override
  public long getLockedDone(int rollupId, Hash txHash) throws Exception {
    return liszt.lockDoneTimeout(rollupId, txHash);
  }

  @Override
  public TransferDone getTransferDone(short rollupId, Hash hash) throws Exception {
    return liszt.transferDone(rollupId, hash);
  }

  @Override
  public BigInteger getBlockHeight() {
    return deployer.getBlockHeight();
  }
}

package net.consensys.liszt.server;

import java.math.BigInteger;
import net.consensys.liszt.accountmanager.AccountService;
import net.consensys.liszt.accountmanager.AccountServiceImp;
import net.consensys.liszt.blockchainmanager.BlockchainService;
import net.consensys.liszt.blockchainmanager.BlockchainServiceImp;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.PublicKey;
import net.consensys.liszt.core.crypto.Signature;
import net.consensys.liszt.provermanager.ProverService;
import net.consensys.liszt.provermanager.ProverServiceImp;
import net.consensys.liszt.transfermanager.BatchService;
import net.consensys.liszt.transfermanager.BatchServiceImpl;
import net.consensys.liszt.transfermanager.TransferService;
import net.consensys.liszt.transfermanager.TransferServiceImpl;

public class Start {

  public static void main(String[] args) {
    TransferService transferService = new TransferServiceImpl();
    AccountService accountService = new AccountServiceImp(null, null);
    BatchService batchService = new BatchServiceImpl();
    ProverService proveService = new ProverServiceImp();
    BlockchainService blockchainService = new BlockchainServiceImp();

    Controller controller =
        new ControllerImp(
            transferService, accountService, batchService, proveService, blockchainService);

    RTransfer rtTransfer = createTransfer();
    //  controller.addTransfer(rtTransfer);
  }

  static RTransfer createTransfer() {
    return new RTransfer(
        0,
        new PublicKey("Alice"),
        new PublicKey("Bob"),
        BigInteger.valueOf(100),
        0,
        0,
        new Signature());
  }
}

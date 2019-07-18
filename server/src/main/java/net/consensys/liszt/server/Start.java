package net.consensys.liszt.server;

import java.math.BigInteger;
import net.consensys.liszt.accountmanager.AccountService;
import net.consensys.liszt.accountmanager.AccountServiceImp;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.PublicKey;
import net.consensys.liszt.core.crypto.Signature;
import net.consensys.liszt.provemanager.ProveService;
import net.consensys.liszt.provemanager.ProveServiceImp;
import net.consensys.liszt.transfermanager.BatchService;
import net.consensys.liszt.transfermanager.BatchServiceImpl;
import net.consensys.liszt.transfermanager.TransferService;
import net.consensys.liszt.transfermanager.TransferServiceImpl;

public class Start {

  public static void main(String[] args) {
    TransferService transferService = new TransferServiceImpl();
    AccountService accountService = new AccountServiceImp();
    BatchService batchService = new BatchServiceImpl();
    ProveService proveService = new ProveServiceImp();
    Controller controller =
        new ControllerImp(transferService, accountService, batchService, proveService);
    RTransfer rtTransfer = createTransfer();
    controller.addTransfer(rtTransfer);
  }

  static RTransfer createTransfer() {
    return new RTransfer(
        0, new PublicKey(), new PublicKey(), BigInteger.valueOf(100), 0, 0, new Signature());
  }
}

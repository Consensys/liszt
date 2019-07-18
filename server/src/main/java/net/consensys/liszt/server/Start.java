package net.consensys.liszt.server;

import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.PublicKey;
import net.consensys.liszt.core.crypto.Signature;

import java.math.BigInteger;

public class Start {

  public static void main(String[] args) {
    Controller controller = new ControllerImp(null, null);
    RTransfer rtTransfer = createTransfer();
    // controller.addTransfer(rtTransfer);
  }

  static RTransfer createTransfer() {
    return new RTransfer(0, new PublicKey(), new PublicKey(), BigInteger.valueOf(100), 0, 0, new Signature());
  }
}

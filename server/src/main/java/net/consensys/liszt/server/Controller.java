package net.consensys.liszt.server;

import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.PublicKey;
import net.consensys.liszt.core.crypto.Signature;
import net.consensys.liszt.server.dto.Acccount;
import net.consensys.liszt.server.dto.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

  @Autowired private LisztManager manager;

  @GetMapping("/accounts/{owner}")
  public Acccount accounts(@PathVariable String owner) {
    net.consensys.liszt.accountmanager.Account rollupAccount =
        manager.getAccount(new PublicKey(owner));
    return new Acccount(rollupAccount.publicKey.owner, rollupAccount.amount, rollupAccount.nonce);
  }

  // Example:
  // curl -X POST localhost:8080/transfers -H 'Content-type:application/json' -d '{"from":
  // "Alice","to": "Bob","amount": "22", "nonce":"0", "rIdFrom":"1", "rIdTo":"1"}'
  @PostMapping("/transfers")
  public long newTransfer(@RequestBody Transfer transfer) {
    RTransfer rTransfer =
        new RTransfer(
            transfer.nonce,
            new PublicKey(transfer.from),
            new PublicKey(transfer.to),
            transfer.amount,
            transfer.rIdFrom,
            transfer.rIdTo,
            new Signature());
    manager.addTransfer(rTransfer);
    return HttpStatus.CREATED.value();
  }
}

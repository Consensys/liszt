package net.consensys.liszt.server;

import java.util.ArrayList;
import java.util.List;
import net.consensys.liszt.accountmanager.Account;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.PublicKey;
import net.consensys.liszt.core.crypto.Signature;
import net.consensys.liszt.server.dto.AcccountInfo;
import net.consensys.liszt.server.dto.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

  @Autowired private LisztManager manager;

  @GetMapping("/accounts/users/{owner}")
  public AcccountInfo accounts(@PathVariable String owner) {
    net.consensys.liszt.accountmanager.Account rollupAccount =
        manager.getAccount(new PublicKey(owner));
    return new AcccountInfo(
        rollupAccount.publicKey.owner, rollupAccount.amount, rollupAccount.nonce);
  }

  // http://localhost:8080/accounts/lock
  @GetMapping("/accounts/lock")
  public List<AcccountInfo> lockedAccounts() {
    List<Account> rollupAccount = manager.getLockAccounts();

    List<AcccountInfo> accs = new ArrayList<>();
    for (Account a : rollupAccount) {
      accs.add(new AcccountInfo(a.publicKey.owner, a.amount, a.nonce));
    }
    return accs;
  }

  // Example:
  // curl -X POST localhost:8080/transfers -H 'Content-type:application/json' -d
  // '{"from":"Alice","to": "Bob","amount": "22", "nonce":"0", "rIdFrom":"1", "rIdTo":"1"}'
  @PostMapping("/transfers")
  public String newTransfer(@RequestBody Transfer transfer) {
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
    return rTransfer.hash.asHex;
  }
}

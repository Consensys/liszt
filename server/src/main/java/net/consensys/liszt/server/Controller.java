package net.consensys.liszt.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.consensys.liszt.accountmanager.Account;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.PublicKey;
import net.consensys.liszt.core.crypto.Signature;
import net.consensys.liszt.server.dto.AcccountInfo;
import net.consensys.liszt.server.dto.Transfer;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

  private static final LisztManager manager = new LisztManagerImp((short) 0, (short) 1);

  @GetMapping("/accounts/users/{owner}")
  public AcccountInfo accounts(@PathVariable String owner) {
    net.consensys.liszt.accountmanager.Account rollupAccount =
        manager.getAccount(new PublicKey(owner));
    return new AcccountInfo(
        rollupAccount.publicKey.hash.asHex, rollupAccount.amount, rollupAccount.nonce);
  }

  // http://localhost:8080/accounts/lock
  @GetMapping("/accounts/lock")
  public List<AcccountInfo> lockedAccounts() {
    List<Account> rollupAccount = manager.getLockAccounts();

    List<AcccountInfo> accs = new ArrayList<>();
    for (Account a : rollupAccount) {
      accs.add(new AcccountInfo(a.publicKey.hash.asHex, a.amount, a.nonce));
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
            new Signature(),
            100,
            Optional.empty());
    manager.addTransfer(rTransfer);
    return rTransfer.hash.asHex;
  }
}

package net.consensys.liszt.server;

import static spark.Spark.*;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.consensys.liszt.accountmanager.Account;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.PublicKey;
import net.consensys.liszt.core.crypto.Signature;
import net.consensys.liszt.server.dto.AcccountInfo;
import net.consensys.liszt.server.dto.Transfer;

public class Controller {

  private static final LisztManager manager = new LisztManagerImp((short) 0, (short) 1);

  public Controller() {
    get(
        "/accounts/users/:owner",
        (req, res) -> {
          net.consensys.liszt.accountmanager.Account rollupAccount =
              manager.getAccount(new PublicKey(req.params(":owner")));
          return new Gson()
              .toJson(
                  new AcccountInfo(
                      rollupAccount.publicKey.hash.asHex,
                      rollupAccount.amount,
                      rollupAccount.nonce));
        });

    get(
        "/accounts/lock",
        (req, res) -> {
          List<Account> rollupAccount = manager.getLockAccounts();

          List<AcccountInfo> accs = new ArrayList<>();
          for (Account a : rollupAccount) {
            accs.add(new AcccountInfo(a.publicKey.hash.asHex, a.amount, a.nonce));
          }
          return new Gson().toJson(accs);
        });

    post(
        "/transfers",
        (request, response) -> {
          response.type("application/json");

          Transfer transfer = new Gson().fromJson(request.body(), Transfer.class);

          Optional<String> hashOfThePendingTransfer = Optional.empty();
          if (transfer.hashOfThePendingTransfer != null) {
            hashOfThePendingTransfer = Optional.of(transfer.hashOfThePendingTransfer);
          }

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
                  hashOfThePendingTransfer);
          manager.addTransfer(rTransfer);
          return new Gson().toJson(rTransfer.hash.asHex);
        });
  }
}

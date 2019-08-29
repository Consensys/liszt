package net.consensys.liszt.server;

import static spark.Spark.*;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.consensys.liszt.accountmanager.Account;
import net.consensys.liszt.accountmanager.RandomAccountStateProvider;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.PublicKey;
import net.consensys.liszt.core.crypto.Signature;
import net.consensys.liszt.server.dto.AcccountInfo;
import net.consensys.liszt.server.dto.Transfer;

public class Controller {

  private final LisztManager manager;

  public Controller(short rollup0, short rollup1) {

    manager = new LisztManagerImp(rollup0, rollup1, new RandomAccountStateProvider(rollup0));
    get(
        "/accounts/users/:owner",
        (req, res) -> {
          Account rollupAccount = manager.getAccount(new PublicKey(req.params(":owner")));
          return new Gson()
              .toJson(
                  new AcccountInfo(
                      rollupAccount.publicKey.owner,
                      rollupAccount.amount,
                      rollupAccount.nonce,
                      rollupAccount.isLock,
                      rollupAccount.publicKey.hash.asHex));
        });

    get(
        "/accounts",
        (req, res) -> {
          List<Account> rollupAccounts = manager.getAccounts();

          List<AcccountInfo> accs = new ArrayList<>();
          for (Account a : rollupAccounts) {
            accs.add(
                new AcccountInfo(
                    a.publicKey.owner, a.amount, a.nonce, a.isLock, a.publicKey.hash.asHex));
          }
          return new Gson().toJson(accs);
        });

    post(
        "/transfers",
        (request, response) -> {
          //  System.out.println("Transfer");
          // System.out.println(request.body());
          response.type("application/json");

          Transfer transfer = new Gson().fromJson(request.body(), Transfer.class);
          // PublicKey from = new PublicKey(transfer.from);
          // Optional<String> hashOfThePendingTransfer = Optional.empty();
          // if (transfer.hashOfThePendingTransfer != null) {
          // hashOfThePendingTransfer = Optional.of(transfer.hashOfThePendingTransfer);
          // if (transfer.hashOfThePendingTransfer.equals(transfer.from)) {
          //  PublicKey from = new PublicKey(new Hash(transfer.from));
          // }
          // }

          PublicKey from = new PublicKey(new Hash(transfer.from));
          Optional<String> hashOfThePendingTransfer = Optional.empty();
          if (transfer.hashOfThePendingTransfer != null) {
            hashOfThePendingTransfer = Optional.of(transfer.hashOfThePendingTransfer);
          }

          RTransfer rTransfer =
              new RTransfer(
                  transfer.nonce,
                  from,
                  new PublicKey(new Hash(transfer.to)),
                  transfer.amount,
                  transfer.rIdFrom,
                  transfer.rIdTo,
                  new Signature(),
                  100,
                  hashOfThePendingTransfer);
          boolean isValid = manager.addTransfer(rTransfer);
          return new Gson().toJson(new Response(rTransfer.hash.asHex, isValid));
          // return new Gson().toJson("OK");
        });
  }
}

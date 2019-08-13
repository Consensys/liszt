/*
 * Copyright 2019 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package net.consensys.liszt.blockchainmanager.ganache;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.consensys.liszt.blockchainmanager.Controller;
import net.consensys.liszt.blockchainmanager.EthAccount;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class GanacheController implements Controller {

  final String keysPath =
      "/tmp/keys.json"; // System.getProperty("user.dir") + "/src/main/resources/keys.json";

  final String contractAddrPath = "/tmp/addr.txt";
  private static final String ganache_cli = "ganache-cli";

  private static final String DEFAULT_HOST_NAME = "http://127.0.0.1";
  private static final String DEFAULT_PORT = "8545";

  private static final String BALANCE_PARAM = "-e";
  private static final String ACCOUNT_SIZE_PARAM = "-a";
  private static final String KEY_PATH_PARAM = "--acctKeys";

  private List<EthAccount> accounts;
  private final String provider;
  private Process process;
  private final int accountSize;
  private final int balance;
  private static final Logger logger = LogManager.getLogger("Liszt");

  public GanacheController(int accountSize, int balance) throws Exception {
    provider = DEFAULT_HOST_NAME + ":" + DEFAULT_PORT;
    this.accountSize = accountSize;
    this.balance = balance;
  }

  @Override
  public String getContractAddress() throws IOException {
    List<String> addrLs = Files.readAllLines(Paths.get(contractAddrPath));
    return addrLs.get(0);
  }

  @Override
  public void saveContractAddress(String addr) throws FileNotFoundException {
    try (PrintWriter out = new PrintWriter(contractAddrPath)) {
      out.println(addr);
    }
  }

  @Override
  public boolean isLocalNodeStarted() {
    File keyFile = new File(keysPath);
    return keyFile.exists();
  }

  @Override
  public void start() throws Exception {
    logger.info("Starting ganache");
    // starts a child process of ganache-cli and generates a keys.json file
    ProcessBuilder pb =
        new ProcessBuilder(
            ganache_cli,
            ACCOUNT_SIZE_PARAM,
            "" + accountSize,
            BALANCE_PARAM,
            "" + balance,
            KEY_PATH_PARAM,
            keysPath);

    process = pb.start();

    // register shut down hook
    shutDown();
    // waits for keys.json for 10 second and then reads in keys.json from a file
    initKeys();
  }

  // Wait for keys.json file to be copied to the keysPath directory
  @SuppressWarnings({"unchecked", "DefaultCharset"})
  private void initKeys() throws Exception {
    accounts = new ArrayList<EthAccount>();
    File keyFile = new File(keysPath);
    int waitInterval = 0;
    while (waitInterval < 30) {
      if (keyFile.exists()) break;
      Thread.sleep(500);
      waitInterval++;
      logger.info("Waiting for ETH private keyss to be generated");
    }
  }

  public void shutDown() {
    // cleans up process and deletes keys.json
    Thread ganacheThread;
    ganacheThread = new Thread(() -> cleanup());
    // calls the cleanup thread after a shutdown signal is detected
    Runtime.getRuntime().addShutdownHook(ganacheThread);
  }

  private synchronized void cleanup() {
    if (process.isAlive()) {
      process.destroy();
      new File(keysPath).delete();
    }
  }

  public List<EthAccount> accounts() throws Exception {

    List<EthAccount> accounts = new ArrayList<>();
    JSONParser parser = new JSONParser();
    JSONObject accountsJSON =
        (JSONObject) ((JSONObject) parser.parse(new FileReader(keysPath))).get("private_keys");
    Set<String> keys = accountsJSON.keySet();
    for (String key : keys) {
      accounts.add(new EthAccount(key, accountsJSON.get(key).toString()));
    }

    return accounts;
  }

  public String provider() {
    return provider;
  }
}

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
package net.consensys.liszt.blockchainmanager;

import net.consensys.liszt.blockchainmanager.contract.LisztContract;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

public class LisztDeployer implements Deployer {

  // private final RemoteCall<LisztContract> liszt;
  private final Web3j web3j;
  private final ContractGasProvider contractGasProvider;

  public LisztDeployer(String provider) {
    this.web3j = Web3j.build(new HttpService(provider));
    this.contractGasProvider = new DefaultGasProvider();
  }

  @Override
  public void saveContractAddress() {}

  public LisztContract deploySmartContract(String privateKey) {
    try {
      Credentials credentials = Credentials.create(privateKey);
      RemoteCall<LisztContract> liszt =
          LisztContract.deploy(web3j, credentials, contractGasProvider);
      LisztContract lisztContract = liszt.send();
      //   lisztContract.getContractAddress();
      return lisztContract;
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
    return null;
  }

  @Override
  public LisztContract loadSmartContract(String privateKey, String addr) {
    Credentials credentials = Credentials.create(privateKey);

    LisztContract lisztContract = LisztContract.load(addr, web3j, credentials, contractGasProvider);
    return lisztContract;
  }
}

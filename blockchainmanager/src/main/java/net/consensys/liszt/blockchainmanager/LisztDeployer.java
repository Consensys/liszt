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

    private final RemoteCall<LisztContract> liszt;

    public LisztDeployer(String privateKey, String provider) {
        Web3j web3j = Web3j.build(new HttpService(provider));

        Credentials credentials = Credentials.create(privateKey);

        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        liszt = LisztContract.deploy(
                web3j,
                credentials,
                contractGasProvider
        );
    }

    public LisztContract deploySmartContract() {
        try {
            liszt.send();
            return liszt.send();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }
}

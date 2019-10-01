[![CircleCI](https://circleci.com/gh/ConsenSys/liszt/tree/master.svg?style=svg&circle-token=90012bf375be38472157803f010025c532ae2ddf)](https://circleci.com/gh/ConsenSys/liszt/tree/master)
# liszt:
Liszt is a version of zk-rollup (layer 2 scaling sloution for ethereum) which will allow: 

**In ETH 1:**
 - Transfer funds from one zk-roolup to another. (For example Exchange -> Casino) 
 
**In ETH 2**: 
 - Cross shard transfers 
 - Delegated executions, paying for transactions on shard X with funds locked in the rollup in shard Y.

More information can be found here: https://hackmd.io/gmZ_SkryRDOF7JeG6_rpTw?view

You need to install Ganache, for linux use:
```
sudo npm install -g ganache-cli

```
Then you can build by cloning this repository

### Building:
- ```git clone https://github.com/ConsenSys/liszt```
- ```cd liszt```
- ```gradle test```
- ```gradle shadowJar``` 

To run open the console and launch the server with the following command:

### Running REST server:
- ```java  -Xms6000m -Xmx12048m -classpath server/build/libs/liszt-ws.jar net.consensys.liszt.server.Start 0 1 4567```

To have the UI run:
### UI
``` cd lisz/lisz-ui
    yarn install
    yarn run serve
```
You should be able to go to http://localhost:8080/ and start doing transfers

### Architecture:
<img width="760" alt="Screenshot 2019-07-25 at 13 16 49" src="https://user-images.githubusercontent.com/7760067/61870546-a96d5c00-aede-11e9-8858-6db6590187ef.png">

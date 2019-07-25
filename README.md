[![CircleCI](https://circleci.com/gh/ConsenSys/liszt/tree/master.svg?style=svg&circle-token=90012bf375be38472157803f010025c532ae2ddf)](https://circleci.com/gh/ConsenSys/liszt/tree/master)
# liszt:
Liszt is a version of zk-rollup (layer 2 scaling sloution for ethereum) which will allow: 

**In ETH 1:**
 - Transfer funds from one zk-roolup to another. (For example Exchange -> Casino) 
 
**In ETH 2**: 
 - Cross shard transfers 
 - Delegated executions, paying for transactions on shard X with funds locked in the rollup in shard Y.

More information can be found here: https://hackmd.io/gmZ_SkryRDOF7JeG6_rpTw?view

### Building:
- ```git clone https://github.com/ConsenSys/liszt```
- ```gradle test```

### Architecture:
<img width="760" alt="Screenshot 2019-07-25 at 13 16 49" src="https://user-images.githubusercontent.com/7760067/61870546-a96d5c00-aede-11e9-8858-6db6590187ef.png">

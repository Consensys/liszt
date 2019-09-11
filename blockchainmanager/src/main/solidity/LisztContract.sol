pragma solidity ^0.5.9;
//pragma solidity >=0.4.20;
//solc LisztContract.sol --bin --abi --optimize -o . --overwrite
//web3j solidity generate -b LisztContract.bin -a LisztContract.abi -o . -p net.consensys.liszt.blockchainmanager.contract

contract LisztContract  {

// xTransaction
   struct transaction {
          uint amount;
          string from;
          string to;
          bool exists;
          uint8 sourceRollupId;
          uint8 targetRollupId;
          uint timeout;

        }

   struct transferComplete{
          bytes32 recipient;
          bytes32 to;
          uint amount;
          bool exists;
   }


    mapping(uint8 =>mapping(bytes32 => uint)) lockDone;
    mapping(uint8 => mapping(bytes32 => transferComplete)) public transferDone;
    mapping(uint8 => bytes32) public lastRootHash;

    function lockTimeout(uint8 rollupId, bytes32  hash) public view returns (uint){
        return lockDone[rollupId][hash];
    }


    function update(bytes32[] memory from_list,
        bytes32[] memory to_list,
        uint[] memory amount_list,
      //  uint8[] memory sourceRollupId_list,
        uint8[] memory targetRollupId_list,
        bytes32[] memory hash_list,
        uint[] memory timeout_list,
        bytes32[] memory pending_transfer_hash_list,
        uint8 rollupId,
        bytes32 rootHash) public {

        uint nbOfTranfers = amount_list.length;

        for (uint i=0; i<nbOfTranfers; i++) {
            uint targetRollupId = targetRollupId_list[i];

            if(rollupId != targetRollupId){
               lockDone[rollupId][hash_list[i]] = timeout_list[i];
            }

            bytes32 pending_transfer_hash  = pending_transfer_hash_list[i];

            if(pending_transfer_hash != 0){
                transferDone[rollupId][pending_transfer_hash] = transferComplete(from_list[i], to_list[i], amount_list[i], true);
            }
        }
        lastRootHash[rollupId] = rootHash;
    }
}
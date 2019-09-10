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



    function updateLockDone(uint8[] memory sourceRollupId_list, uint8[] memory targetRollupId_list, uint[] memory timeout_list, bytes32[] memory hash_list) public {
          //  if (sourceRollupId != targetRollupId){
          //      lockDone[sourceRollupId][hash] = timeout;
          //  }
          for (uint i=0; i<sourceRollupId_list.length; i++) {
                lockDone[sourceRollupId_list[i]][hash_list[i]] = timeout_list[i];
          }

    }

    function lockTimeout(uint8 rollupId, bytes32  hash) public view returns (uint){
        return lockDone[rollupId][hash];
    }



    function updateTransferDone(uint8[] memory rollupId_list, bytes32[] memory from_list, bytes32[] memory to_list, uint[] memory amount_list, bytes32[] memory hash_list) public  {
           for (uint i=0; i<rollupId_list.length; i++) {
                uint8 rollupId = rollupId_list[i];
                bytes32 from = from_list[i];
                bytes32 to = to_list[i];
                uint amount = amount_list[i];
                bytes32 hash = hash_list[i];
                transferDone[rollupId][hash] = transferComplete(from, to, amount, true);
           }
    }

}
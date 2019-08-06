pragma solidity >=0.4.20;


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
          string from;
          string to;
          uint amount;
          bool exists;
          transaction other;
   }


    mapping(uint8 =>mapping(string => uint)) lockDone;
    mapping(bytes32 => transferComplete) transferDone;



    function updateLockDone(string memory from, string memory to, uint amount, uint8 sourceRollupId, uint8 targetRollupId, uint timeout, string memory hash) public {
            if (sourceRollupId != targetRollupId){
                lockDone[sourceRollupId][hash] = timeout;
            }
    }

    function lockTimeout(uint8 rollupId, string memory hash) public view returns (uint){
        return lockDone[rollupId][hash];
    }


/*
    function updateTransferDone(string memory from, string memory to, uint8 amount, string memory otherFrom, string memory otherTo, uint  otherAmount, uint8 otherSourceRollupId, uint8 otherTargetRollupId) public  {

    }

    function transferDoneContains(string memory otherFrom, string memory otherTo, uint  otherAmount, uint8 otherSourceRollupId, uint8 otherTargetRollupId, uint otherTimeout) public view returns (bool)  {
        transaction memory other = transaction(otherAmount,otherFrom, otherTo, true, otherSourceRollupId, otherTargetRollupId, otherTimeout);
        bytes32  hash = hashTransaction(other);
        return transferDone[hash].exists;
    }

    function hashTransaction(transaction memory  t) private pure returns (bytes32){
        bytes32  hash =  keccak256(abi.encodePacked(t.amount, t.from, t.to, t.sourceRollupId, t.targetRollupId));
        return hash;
    }*/
}
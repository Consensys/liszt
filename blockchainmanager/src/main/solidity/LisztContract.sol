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

        }

   struct transferComplete{
          string from;
          string to;
          uint amount;
          bool exists;
          transaction other;
   }

    LisztContract public liszt;

    mapping(bytes32 => transaction) lockDone;
    mapping(bytes32 => transferComplete) transferDone;


    function setLisztAddress(address addr) public{
        liszt = LisztContract(addr);
    }

    function updateLockDone(string memory from, string memory to, uint amount, uint8 sourceRollupId, uint8 targetRollupId) public {
            if (sourceRollupId != targetRollupId){
                transaction memory t = transaction(amount,from, to, true, sourceRollupId, targetRollupId);
                bytes32  hash = hashTransaction(t);
                lockDone[hash] = t;
            }
    }

    function lockDoneContains(string memory from, string memory to, uint  amount, uint8 sourceRollupId, uint8 targetRollupId) public view returns (bool){
        bytes32  hash =  keccak256(abi.encodePacked(amount, from,to, sourceRollupId, targetRollupId));
        if (lockDone[hash].exists) {
             return true;
        }
        return false;
    }


    function updateTransferDone(string memory from, string memory to, uint8 amount, string memory otherFrom, string memory otherTo, uint  otherAmount, uint8 otherSourceRollupId, uint8 otherTargetRollupId) public  {
        bool otherContains  = liszt.lockDoneContains(otherFrom,otherTo,otherAmount,otherSourceRollupId,otherTargetRollupId);
        if (otherContains) {
            transaction memory other = transaction(otherAmount,otherFrom, otherTo, true, otherSourceRollupId, otherTargetRollupId);
            bytes32  hash = hashTransaction(other);
            transferComplete memory completed = transferComplete(from, to, amount, true, other) ;
            transferDone[hash] = completed;
        }
    }

    function transferDoneContains(string memory otherFrom, string memory otherTo, uint  otherAmount, uint8 otherSourceRollupId, uint8 otherTargetRollupId) public view returns (bool)  {
        transaction memory other = transaction(otherAmount,otherFrom, otherTo, true, otherSourceRollupId, otherTargetRollupId);
        bytes32  hash = hashTransaction(other);
        return transferDone[hash].exists;
    }

    function hashTransaction(transaction memory  t) private pure returns (bytes32){
        bytes32  hash =  keccak256(abi.encodePacked(t.amount, t.from, t.to, t.sourceRollupId, t.targetRollupId));
        return hash;
    }
}
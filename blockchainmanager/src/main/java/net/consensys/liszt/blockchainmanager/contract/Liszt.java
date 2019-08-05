package net.consensys.liszt.blockchainmanager.contract;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;

public class Liszt {

    private final LisztContract lisztContract;

    public Liszt(LisztContract lisztContract) {
        this.lisztContract = lisztContract;
    }

    public TransactionReceipt updateLockDone(XTransfer t) throws Exception {
        return lisztContract.updateLockDone(t.from,
                t.to,
                t.amount,
                BigInteger.valueOf(t.sourceRollupId),
                BigInteger.valueOf(t.targetRollupId)
        ).send();
    }

    public boolean lockDoneContains(XTransfer t) throws Exception {
        return lisztContract.lockDoneContains(t.from,
                t.to,
                t.amount,
                BigInteger.valueOf(t.sourceRollupId),
                BigInteger.valueOf(t.targetRollupId)
        ).send();
    }

    public void setLisztAddress(String addr) throws Exception{
        lisztContract.setLisztAddress(addr).send();
    }

    public String addr(){
        return lisztContract.getContractAddress();
    }

    public void updateTransferDone(TransferComplete t) throws Exception {
        lisztContract.updateTransferDone(t.from,
                t.to,
                t.amount,
                t.other.from,
                t.other.to,
                t.other.amount,
                BigInteger.valueOf(t.other.sourceRollupId),
                BigInteger.valueOf(t.other.targetRollupId)
        ).send();
    }

    public boolean transferDoneContains(XTransfer t) throws Exception {
        return lisztContract.lockDoneContains(t.from,
                t.to,
                t.amount,
                BigInteger.valueOf(t.sourceRollupId),
                BigInteger.valueOf(t.targetRollupId)
        ).send();
    }

}

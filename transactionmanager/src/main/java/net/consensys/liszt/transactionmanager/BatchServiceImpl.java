package net.consensys.liszt.transactionmanager;

import java.util.List;

public class BatchServiceImpl implements BatchService {


    @Override
    public void addToBatch(List<RTransfer> transfers, byte[] rootHash) {}

    @Override
    public BatchState startNewBatch(byte[] fatherRootHash) {
        return null;
    }

    @Override
    public Batch getBatchToProve() {
        return null;
    }

    @Override
    public void storeGeneratedProof(byte[] roothash, byte[] proof) {}

    @Override
    public byte[] generateTransaction(byte[] roothash) {
        return new byte[0];
    }
}

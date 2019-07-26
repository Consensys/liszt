package net.consensys.liszt.server;

import java.util.ArrayList;
import java.util.List;
import net.consensys.liszt.accountmanager.AccountService;
import net.consensys.liszt.blockchainmanager.*;
import net.consensys.liszt.core.common.Batch;
import net.consensys.liszt.core.common.RTransfer;
import net.consensys.liszt.core.crypto.Hash;
import net.consensys.liszt.core.crypto.Proof;
import net.consensys.liszt.provermanager.ProverService;
import net.consensys.liszt.transfermanager.BatchService;
import net.consensys.liszt.transfermanager.BatchStatus;
import net.consensys.liszt.transfermanager.RTransferState;
import net.consensys.liszt.transfermanager.TransferService;

public class ControllerImp implements Controller {

  private final TransferService transferService;
  private final AccountService accountService;
  private final BatchService batchService;
  private final ProverService proveService;
  private final BlockchainService blockchainService;

  private Hash lastRootHash;

  public ControllerImp(
      TransferService transferService,
      AccountService accountService,
      BatchService batchService,
      ProverService proveService,
      BlockchainService blockchainService) {
    this.transferService = transferService;
    this.accountService = accountService;
    this.batchService = batchService;
    this.proveService = proveService;
    this.blockchainService = blockchainService;
    lastRootHash = null; // new Hash();
  }

  @Override
  public boolean addTransfer(RTransfer rtx) {
    if (!accountService.checkBasicValidity(rtx, this.lastRootHash)) {
      return false;
    }

    transferService.addTransfer(rtx);

    List<RTransfer> transfers;
    List<RTransfer> invalidTransfers = new ArrayList<>();
    do {
      transfers = transferService.selectRTransfersForNextBatch(this.lastRootHash, invalidTransfers);
      if (!transfers.isEmpty()) {
        break;
      }
      invalidTransfers = accountService.updateIfAllTransfersValid(transfers, this.lastRootHash);
    } while (!transfers.isEmpty() && invalidTransfers.isEmpty());

    Hash newRootHash = accountService.getLastAcceptedRootHash();
    batchService.startNewBatch(lastRootHash, newRootHash, transfers);
    Batch batch = batchService.getBatchToProve();
    this.lastRootHash = newRootHash;
    proveService.proveBatch(batch);
    return true;
  }

  @Override
  public void onNewProof(Proof proof) {
    Batch batch = batchService.getBatch(proof.rootHash);
    blockchainService.submit(batch, proof);
  }

  @Override
  public void onChainReorg(Hash rootHash) {
    this.lastRootHash = rootHash;
  }

  @Override
  public void onBatchIncluded(Batch batch, int blockHight, Hash blockHash) {
    batchService.updateBatchStatus(batch, blockHight, blockHash);
  }

  @Override
  public RTransferState getRTransferStatus(RTransfer transfer) {
    List<BatchStatus> batchStates = batchService.getBatchesForTransfer(transfer);
    RTransferState rTransferState = new RTransferState(transfer, batchStates);
    return rTransferState;
  }
}

package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;

public interface TransferDao {

    Transfer getTransferByTransferId(int transferId);

    void sendTransfer(Transfer transfer, int accountFromId, int accountToId, BigDecimal amount);

    void requestTransfer(Transfer transfer, int accountFromId, int accountToId, BigDecimal amount);
}

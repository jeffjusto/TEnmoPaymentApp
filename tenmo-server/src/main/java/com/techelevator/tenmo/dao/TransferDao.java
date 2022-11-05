package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;


public interface TransferDao {

    Transfer getTransferByTransferId(int transferId);

    //I don't think we need all of these parameters, but I'm unsure of which ones to keep. If we send a Transfer object,
    // it should already have all the other data. And we may be able to skip accountFrom either way since it will
    //always be the logged in user

    //also, if we do pass a transfer object as the parameter, we can combine send and request into one method as just "addTransfer"
    // since the Transfer will have a transfer_type_id

    void sendTransfer(Transfer transfer);

    void requestTransfer(Transfer transfer);

    List<Transfer> getTransferHistory(int user_id);

    List<Transfer> getPendingTransfers(int user_id);
}

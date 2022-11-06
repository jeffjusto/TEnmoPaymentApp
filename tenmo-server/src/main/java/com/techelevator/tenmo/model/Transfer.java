package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
    private int transferId;
    private int transferTypeId;
    private int transferStatusId;
    private int accountFrom;
    private int accountTo;

    private BigDecimal amount;

//    public Transfer(int transferId, int transferTypeId, int transferStatusId, int accountFrom, int accountTo, BigDecimal amount) {
//        this.transferId = transferId;
//        this.transferTypeId = transferTypeId;
//        this.transferStatusId = transferStatusId;
//        this.accountFrom = accountFrom;
//        this.accountTo = accountTo;
//        this.amount = amount;
//    }

//    public Transfer(int transferTypeId, int transferStatusId, int accountFrom, int accountTo, BigDecimal amount) {
//        this.transferTypeId = transferTypeId;
//        this.transferStatusId = transferStatusId;
//        this.accountFrom = accountFrom;
//        this.accountTo = accountTo;
//        this.amount = amount;
//    }

//    public Transfer() {
//    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFromId) {
        this.accountFrom = accountFromId;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountToId) {
        this.accountTo = accountToId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public int getAccountIdByUserId(int userId){
        return userId;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "transfer_id='" + transferId + '\'' +
                ", transfer_type_id='" + transferTypeId + '\'' +
                ", transfer_status_id='" + transferStatusId + '\'' +
                ", account_from='" + accountFrom + '\'' +
                ", account_to='" + accountTo + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }


}

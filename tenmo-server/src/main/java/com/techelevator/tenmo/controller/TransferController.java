package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class TransferController {
    private TransferDao transferDao;

    public TransferController(TransferDao transferDao) {
        this.transferDao = transferDao;
    }


    @RequestMapping (path = "/transfer/send", method = RequestMethod.POST)
    public void sendTransfer (@RequestBody Transfer transfer){
        transferDao.sendTransfer(transfer);
    }

    @RequestMapping (path = "/transfer/request", method = RequestMethod.POST)
    public void requestTransfer (@RequestBody Transfer transfer){
        transferDao.requestTransfer(transfer);
    }

    @RequestMapping (path = "transfer/all", method = RequestMethod.GET)
    public List<Transfer> getTransferHistory(int user_id) {
        return transferDao.getTransferHistory(user_id);
    }

    @RequestMapping (path = "transfer/pending", method = RequestMethod.GET)
    public List<Transfer> getPendingTransfers(int user_id) {
        return transferDao.getPendingTransfers(user_id);
    }







}

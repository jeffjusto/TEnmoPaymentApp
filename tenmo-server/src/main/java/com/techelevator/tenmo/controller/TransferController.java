package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class  TransferController {
    private TransferDao transferDao;

    public TransferController(TransferDao transferDao) {
        this.transferDao = transferDao;
    }

    @ResponseStatus (HttpStatus.CREATED)
    @RequestMapping (path = "/transfer/send", method = RequestMethod.POST)
    public void sendTransfer (@RequestBody Transfer transfer){
        transferDao.sendTransfer(transfer);
    }

    @RequestMapping (path = "/transfer/request", method = RequestMethod.POST)
    public void requestTransfer (@RequestBody Transfer transfer){
        transferDao.requestTransfer(transfer);
    }

    @RequestMapping (path = "transfer/all", method = RequestMethod.GET)
    public List<Transfer> getTransferHistory(Principal principal) {
        return transferDao.getTransferHistory(principal);
    }

    @RequestMapping (path = "transfer/pending", method = RequestMethod.GET)
    public List<Transfer> getPendingTransfers(Principal principal) {
        return transferDao.getPendingTransfers(principal);
    }

    private int getAccountIdFromUsername(Principal principal){
        return transferDao.getAccountIdFromUsername(principal.getName());
    }







}

package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;


@PreAuthorize("isAuthenticated()")
public class AccountController {

    private AccountDao accountDao;
    private UserDao userDao;

    public AccountController(AccountDao accountDao, UserDao userDao){
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/account", method = RequestMethod.GET)
    public Account getAccount() throws AccountNotFoundException {
        return accountDao.getAccount();
    }

    @RequestMapping(path = "/account/{accountId}", method = RequestMethod.GET)
    public Account getAccountByAccountId(@PathVariable int accountId){
        Account account = accountDao.getAccountByUserId(accountId);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Not Found");
        } else {
            return account;
        }
    }

    @RequestMapping(path = "/account/user/{userId}", method = RequestMethod.GET)
    public Account getAccountByUserId(@PathVariable int userId){
        Account account = accountDao.getAccountByUserId(userId);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Not Found");
        } else {
            return account;
        }
    }
    @RequestMapping(path = "/account/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(){
        Account account = accountDao.getAccountByAccountId(accountId);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Not Found");
        } else {
            return account.getBalance();
        }
    }


}

package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;

public interface AccountDao {

    Account getAccount() throws AccountNotFoundException;

    Account getAccountByUserId(int userId);

    Account getAccountByAccountId(int accountId);

    BigDecimal getBalance(int accountId);

    /*
    changed the parameters below to make more sense of adding and subtracting amounts of accounts and probably not
    necessary to add to the AccountController
     */

    BigDecimal addToBalanceByAccountId(int accountId, BigDecimal amount);

    BigDecimal subtractFromBalanceByAccountId(int accountId, BigDecimal amount);
}

package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;

public interface AccountDao {

    Account getAccount() throws AccountNotFoundException;

    Account getAccountByAccountId(int AccountId);

    Account getAccountByUserId(int userId);

    BigDecimal getBalance(User user);

    /*
    changed the parameters below to make more sense of adding and subtracting amounts of accounts and probably not
    necessary to add to the AccountController
     */

    BigDecimal addToBalanceByAccountId(int userId, BigDecimal amount);

    BigDecimal subtractFromBalanceByAccountId(int userId, BigDecimal amount);
}

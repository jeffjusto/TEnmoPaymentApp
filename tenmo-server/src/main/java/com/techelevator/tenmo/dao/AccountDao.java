package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {

    Account getAccount();

    Account getAccountByUserId(int userId);

    Account getAccountByAccountId(int accountId);

    BigDecimal getBalance(int accountId);

    void addToBalanceByUserId(Account account, BigDecimal balance, int userId);

    void subtractFromBalanceByUserId(Account account, BigDecimal balance, int userId);
}

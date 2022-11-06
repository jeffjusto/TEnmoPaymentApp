package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Account;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
    public class JdbcAccountDao implements AccountDao {

    //JDBC Template copied from JdbcUserDao
    private JdbcTemplate jdbcTemplate;

    //Constructor
    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

        /* Looked at the DAO Testing Exercises, took that format as well as
        the methods intelliJ, for all the methods below, maybe we wont use all of them?
         */

    @Override
    public Account getAccount(int accountId) {
        Account account = null;

        String sql = "SELECT account_id, user_id, balance FROM account where account_id = ?;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountId);
        if (result.next()) {
            account = mapRowToAccount(result);
        }
        return account;
    }



    @Override
    public Account getAccountByUserId(int userId) {
            Account account = null;

            String sql = "SELECT * FROM account WHERE user_id = ?;";

            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
            if (result.next()) {
                account = mapRowToAccount(result);
            }
            return account;
        }



        @Override
        public BigDecimal getBalance ( int accountId){
            BigDecimal balance = null;
            String sql = "SELECT balance FROM account WHERE account_id = ?;";
            try {
                balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, accountId);
            } catch (NullPointerException | EmptyResultDataAccessException e){
                System.out.println("Invalid");
            }
            return balance;
        }

        @Override
        public BigDecimal addToBalanceByAccountId ( int accountId, BigDecimal amount){
            String sql = "UPDATE account " +
                    "SET balance = balance + " + amount +
                    " WHERE account_id = " + accountId + ";";
            //try catch block here?
            return getBalance(accountId);
        }

        @Override
        public BigDecimal subtractFromBalanceByAccountId ( int accountId, BigDecimal amount){
            String sql = "UPDATE account " +
                    "SET balance = balance - " + amount +
                    " WHERE account_id = " + accountId + ";";
            //try catch block here?
            return getBalance(accountId);
        }


        //added to map rows to account and use in table methods above
        private Account mapRowToAccount (SqlRowSet rs){
            Account accnt = new Account();
            accnt.setAccountId(rs.getInt("account_id"));
            accnt.setUserId(rs.getInt("user_id"));
            accnt.setBalance(rs.getBigDecimal("balance"));
            return accnt;
        }
    }




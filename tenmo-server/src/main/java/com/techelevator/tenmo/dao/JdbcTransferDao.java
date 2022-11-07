package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {
    private final JdbcTemplate jdbcTemplate;
    private final int REQUEST_TRANSFER_TYPE_ID = 1;
    private final int SEND_TRANSFER_TYPE_ID = 2;
    private final int PENDING_TRANSFER_STATUS_ID = 1;
    AccountDao accountDao;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        accountDao = new JdbcAccountDao(jdbcTemplate);
    }


//    @Override
//    public TransferDto getTransferByTransferId(int transferId) {
//
//        TransferDto dto = new TransferDto();
//        String sql = "SELECT * from transfer where transfer_id = ?";
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
//        if(results.next()){
//            dto = mapRowToAccount(results);
//        }
//
//        return dto;
//    }

    //see notes on TransferDao about send and request


    @Override
    public Transfer getTransferByTransferId(int transferId) {
        return null;
    }

    @Override
    public void sendTransfer(Transfer transfer) {
        String sql = "Insert into transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
                "Values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, transfer.getTransferTypeId(), transfer.getTransferStatusId(), transfer.getAccountFrom(),
                transfer.getAccountTo(), transfer.getAmount());
        accountDao.addToBalanceByAccountId(transfer.getAccountTo(), transfer.getAmount());
        accountDao.subtractFromBalanceByAccountId(transfer.getAccountFrom(), transfer.getAmount());
    }

    @Override
    public void requestTransfer(Transfer transfer) {
        String sql = "Insert into transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
                "Values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, transfer.getTransferTypeId(), transfer.getTransferStatusId(), transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
    }

    public List<Transfer> getTransferHistory(Principal principal) {
        List<Transfer> transfers = new ArrayList<>();
        int account_id = getAccountIdFromUsername(principal.getName());
        String sql = "SELECT * FROM transfer where account_to = ? OR account_from = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, account_id, account_id);
        while(results.next()){
            transfers.add(mapRowToTransfer(results));
        }
        return transfers;

    }


    public List<Transfer> getPendingTransfers(Principal principal) {
        List<Transfer> transfers = new ArrayList<>();
        int account_id = getAccountIdFromUsername(principal.getName());
        String sql = "SELECT * FROM transfer where transfer_status_id = 1 AND (account_to = ? OR account_from = ?)";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, account_id, account_id);
        while(results.next()){
            transfers.add(mapRowToTransfer(results));
        }
        return transfers;

    }


    private Transfer mapRowToTransfer(SqlRowSet rs){
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setTransferTypeId(rs.getInt("transfer_type_id"));
        transfer.setTransferStatusId(rs.getInt("transfer_status_id"));
        transfer.setAccountFrom(rs.getInt("account_from"));
        transfer.setAccountTo(rs.getInt("account_to"));
        transfer.setAmount(rs.getBigDecimal("amount"));
        return transfer;
    }

    public int getAccountIdFromUsername(String string) {
        int accountId;
        String sql = "Select account_id from account join tenmo_user on tenmo_user.user_id = account.user_id where username = ?";
        accountId = jdbcTemplate.queryForObject(sql, int.class, string);
        return accountId;
    }

    public int getAccountIdByUserId(int userId) {
        userId = 0;
        String sql = "SELECT account_id FROM account WHERE user_id = ?;";
        userId = jdbcTemplate.queryForObject(sql, int.class, userId);
        return userId;
    }
}
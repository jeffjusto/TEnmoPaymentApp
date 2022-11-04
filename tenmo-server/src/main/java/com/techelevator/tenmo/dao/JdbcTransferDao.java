package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {
    private final JdbcTemplate jdbcTemplate;
    private final int REQUEST_TRANSFER_TYPE_ID = 1;
    private final int SEND_TRANSFER_TYPE_ID = 2;
    private final int PENDING_TRANSFER_STATUS_ID = 1;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Transfer getTransferByTransferId(int transferId) {

        Transfer transfer = new Transfer();
        String sql = "SELECT * from transfer where transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if(results.next()){
            transfer = mapRowToAccount(results);
        }

        return transfer;
    }

    //see notes on TransferDao about send and request




    @Override
    public void sendTransfer(Transfer transfer) {
        String sql = "Insert into transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
                "Values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, SEND_TRANSFER_TYPE_ID, PENDING_TRANSFER_STATUS_ID, transfer.getAccountFromId(), transfer.getAccountToId(), transfer.getAmount());
    }

    @Override
    public void requestTransfer(Transfer transfer) {
        String sql = "Insert into transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
                "Values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, REQUEST_TRANSFER_TYPE_ID, PENDING_TRANSFER_STATUS_ID, transfer.getAccountFromId(), transfer.getAccountToId(), transfer.getAmount());
    }

    public List<Transfer> getTransferHistory(int user_id){
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT * FROM transfer where user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user_id);
        while(results.next()){
            transfers.add(mapRowToAccount(results));
        }
        return transfers;

    }

    private Transfer mapRowToAccount (SqlRowSet rs){
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setTransferTypeId(rs.getInt("transfer_type_id"));
        transfer.setTransferStatusId(rs.getInt("transfer_status_id"));
        transfer.setAccountFromId(rs.getInt("account_from"));
        transfer.setAccountToId(rs.getInt("account_to"));
        transfer.setAmount(rs.getBigDecimal("amount"));
        return transfer;
    }
}

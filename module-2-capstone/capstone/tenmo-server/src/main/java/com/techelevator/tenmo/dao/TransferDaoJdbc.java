package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TransferDaoJdbc implements TransferDao {
    private final JdbcTemplate jdbcTemplate;

    public TransferDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // q4
    @Override
    public List<Transfer> getTransfersByUserId(long userId){
//        jdbcTemplate.queryForRowSet(sql);
        List<Transfer> transfers = new ArrayList<>();
        Transfer transfer = null;
        String sql = "select\n" +
                "\t\ttransfer_id\n" +
                "\t\t,tt.transfer_type_desc\n" +
                "\t\t,ts.transfer_status_desc\n" +
                "\t\t,account_from\n" +
                "\t\t,account_to\n" +
                "\t\t,amount\n" +
                "\t\t,ufrom.user_id as from_user_id\n" +
                "\t\t,ufrom.username as from_user_name\n" +
                "\t\t,uto.user_id as to_user_id\n" +
                "\t\t,uto.username as to_user_name\n" +
                "\tfrom\n" +
                "\t\ttransfer t\n" +
                "\t\tjoin account ato\n" +
                "\t\t\ton ato.account_id = t.account_to\n" +
                "\t\tjoin tenmo_user uto\n" +
                "\t\t\ton uto.user_id = ato.user_id\n" +
                "\t\tjoin account afrom\n" +
                "\t\t\ton afrom.account_id = t.account_from\n" +
                "\t\tjoin tenmo_user ufrom\n" +
                "\t\t\ton ufrom.user_id = afrom.user_id\n" +
                "\t\tjoin transfer_type tt\n" +
                "\t\t\ton tt.transfer_type_id = t.transfer_type_id\t\t\n" +
                "\t\tjoin transfer_status ts\n" +
                "\t\t\ton ts.transfer_status_id = t.transfer_status_id\n" +
                "\twhere\n" +
                "\t\tuto.user_id = ?\n" +
                "\t\tor\n" +
                "\t\tufrom.user_id=?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId,userId);
            while (results.next()) {
                transfers.add(mapRowToTransfer(results));
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return transfers;
    }

//


    @Override
    public Transfer getTransfersByTransferID(long TransferId) {
        Transfer transfer = null;
        String sql = "select\n" +
                "\t\ttransfer_id\n" +
                "\t\t,tt.transfer_type_desc\n" +
                "\t\t,ts.transfer_status_desc\n" +
                "\t\t,account_from\n" +
                "\t\t,account_to\n" +
                "\t\t,amount\n" +
                "\t\t,ufrom.user_id as from_user_id\n" +
                "\t\t,ufrom.username as from_user_name\n" +
                "\t\t,uto.user_id as to_user_id\n" +
                "\t\t,uto.username as to_user_name\n" +
                "\tfrom\n" +
                "\t\ttransfer t\n" +
                "\t\tjoin account ato\n" +
                "\t\t\ton ato.account_id = t.account_to\n" +
                "\t\tjoin tenmo_user uto\n" +
                "\t\t\ton uto.user_id = ato.user_id\n" +
                "\t\tjoin account afrom\n" +
                "\t\t\ton afrom.account_id = t.account_from\n" +
                "\t\tjoin tenmo_user ufrom\n" +
                "\t\t\ton ufrom.user_id = afrom.user_id\n" +
                "\t\tjoin transfer_type tt\n" +
                "\t\t\ton tt.transfer_type_id = t.transfer_type_id\t\t\n" +
                "\t\tjoin transfer_status ts\n" +
                "\t\t\ton ts.transfer_status_id = t.transfer_status_id\n" +
                "\twhere\n" +
                "\t\ttransfer_id = ?\n";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, TransferId);
            if (results.next()) {
                return mapRowToTransfer(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return null;
    }

    @Override
    public void createTransfer(Transfer transfer) {
        String sql = "INSERT INTO transfer (transfer_id, transfer_type_Id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(
                sql,
                transfer.getTransferId(),
                transfer.getTransferType(),
                transfer.getTransferStatus(),
                transfer.getUserFrom().getUsername(),
                transfer.getUserTo().getUsername(),
                transfer.getAmount()
        );
    }

    @Override
    public Transfer getTransferByID(long transferId) {
        String sql = "SELECT * FROM transfer WHERE transfer_id = ?";
        Transfer transfer = null;

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
            if (results.next()) {
                transfer = mapRowToTransfer(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return transfer;
    }

    public Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();

        transfer.setTransferId(rowSet.getLong("transfer_id"));
        transfer.setTransferType(rowSet.getString("transfer_type_desc"));
        transfer.setTransferStatus(rowSet.getString("transfer_status_desc"));

        User fromUser = new User();
        fromUser.setId(rowSet.getLong("from_user_id"));
        fromUser.setUsername(rowSet.getString("from_user_name"));
        transfer.setUserFrom(fromUser);

        User toUser = new User();
        toUser.setId(rowSet.getLong("to_user_id"));
        toUser.setUsername(rowSet.getString("to_user_name"));
        transfer.setUserTo(toUser);

        Account fromAccount = new Account();
        fromAccount.setAccount_id(rowSet.getLong("account_from"));
        transfer.setAccountFrom(fromAccount);

        Account toAccount = new Account();
        toAccount.setAccount_id(rowSet.getLong("account_to"));
        transfer.setAccountTo(toAccount);

        transfer.setAmount(rowSet.getBigDecimal("amount"));

        return transfer;
    }

}

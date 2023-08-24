package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.service.TransferService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransferDaoJdbc implements TransferDao {
    private final JdbcTemplate jdbcTemplate;

    public TransferDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Transfer> getTransfersByUserId(long userId){
//        String sql = "";
//        jdbcTemplate.queryForRowSet(sql);
        List<Transfer> transfers = new ArrayList<>();
        Transfer transfer = new Transfer();
        transfer.setTransferId(22L);
        transfer.setAmount(BigDecimal.TEN);
        transfer.setTransferStatus("Pending");
        transfer.setTransferType("Request");
        transfer.setUserTo(new User(1002L, "phil", null, "ROLE_USER"));
        transfer.setUserFrom(new User(userId, "rob", null, "ROLE_USER"));
        transfers.add(transfer);
        return transfers;
    }

    public Transfer mapRowToTransfer(SqlRowSet rowSet){
        Transfer transfer = new Transfer();
        User toUser = new User();
        toUser.setId(rowSet.getLong("to_user_id"));
        toUser.setUsername(rowSet.getString("to_user_name"));
        transfer.setUserTo(toUser);
        return transfer;
    }
}

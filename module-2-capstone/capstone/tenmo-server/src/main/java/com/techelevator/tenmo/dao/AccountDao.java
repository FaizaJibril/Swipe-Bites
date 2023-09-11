package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

public interface AccountDao {
    Account getAccountbyUserID(long id);
    Account findByUsername(String username);
    Account findByUserId(Long userId);
    void update (Account account);

    void updateAccount(Account fromAccount);
}




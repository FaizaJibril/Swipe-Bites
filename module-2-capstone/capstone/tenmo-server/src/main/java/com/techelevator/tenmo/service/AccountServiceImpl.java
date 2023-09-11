package com.techelevator.tenmo.service;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService{
    private final AccountDao accountDao;

    public AccountServiceImpl(AccountDao accountDao){
        this.accountDao = accountDao;
    }

    @Override
    public Account getAccountByUser(User user) {
        return accountDao.getAccountbyUserID(user.getId());
    }
}

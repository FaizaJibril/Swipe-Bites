package com.techelevator.tenmo.service;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDto;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface TransferService {
    List<Transfer> listTransferForUser(User user);


    Transfer getTransfer(User user, long id);
    Transfer sendTransfer(User sender, TransferDto transferDto) throws InsufficientFundsException;
    Transfer getTransferById(long id);
}


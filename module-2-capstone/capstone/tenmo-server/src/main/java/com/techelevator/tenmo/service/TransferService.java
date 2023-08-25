package com.techelevator.tenmo.service;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface TransferService {
    List<Transfer> listTransferForUser(User user);

}


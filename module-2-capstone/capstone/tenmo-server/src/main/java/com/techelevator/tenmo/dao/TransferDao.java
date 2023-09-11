package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {
    List<Transfer> getTransfersByUserId(long userId);


    Transfer getTransfersByTransferID(long TransferId);

    void createTransfer(Transfer transfer);

    Transfer getTransferByID(long transferId);

}
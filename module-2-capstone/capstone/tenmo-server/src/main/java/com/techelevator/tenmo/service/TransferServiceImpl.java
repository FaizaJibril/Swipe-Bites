package com.techelevator.tenmo.service;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferServiceImpl implements TransferService {
    private final TransferDao transferDao;

    public TransferServiceImpl(TransferDao transferDao){
        this.transferDao = transferDao;
    }
    @Override
    public List<Transfer> listTransferForUser(User user){
        return transferDao.getTransfersByUserId(user.getId());
    }
    @Override
    public Transfer getTransfer(User user, long id){
        return transferDao.getTransfersByTransferID(id);
    }
}

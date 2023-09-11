package com.techelevator.tenmo.service;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDto;
import com.techelevator.tenmo.model.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferServiceImpl implements TransferService {
    private final TransferDao transferDao;
    private final UserService userService;
    private final AccountDao accountDao;

    public TransferServiceImpl(TransferDao transferDao, UserService userService, AccountDao accountDao) {
        this.transferDao = transferDao;
        this.userService = userService;
        this.accountDao = accountDao;
    }

    @Override
    public List<Transfer> listTransferForUser(User user) {
        return transferDao.getTransfersByUserId(user.getId());
    }

    @Override
    public Transfer getTransfer(User user, long id){
        return transferDao.getTransfersByTransferID(id);
    }


    @Override
    public Transfer sendTransfer(User sender, TransferDto transferDto) throws InsufficientFundsException {
        User toUser = userService.getCurrent(new User(transferDto.getUserTo(), null, null, null));
        User fromUser = userService.getCurrent(new User(transferDto.getUserFrom(), null, null, null));
        Account toAccount = accountDao.getAccountbyUserID(toUser.getId());
        Account fromAccount = accountDao.getAccountbyUserID(fromUser.getId());

        BigDecimal transferAmount = transferDto.getAmount();
        BigDecimal senderBalance = fromAccount.getBalance();

        if (senderBalance.compareTo(transferAmount) >= 0) {
            fromAccount.setBalance(senderBalance.subtract(transferAmount));
            toAccount.setBalance(toAccount.getBalance().add(transferAmount));

            accountDao.updateAccount(fromAccount);
            accountDao.updateAccount(toAccount);

            Transfer transfer = new Transfer();
            transfer.setUserFrom(fromUser);
            transfer.setUserTo(toUser);
            transfer.setAmount(transferAmount);
            transfer.setTransferType("Send");
            transfer.setTransferStatus("Approved");
            transferDao.createTransfer(transfer);

            return transfer;
        } else {
            throw new InsufficientFundsException("Insufficient funds for transfer.");
        }
    }

//    @Override
//    public Transfer getTransferByID(long transferId) {
//        return transferDao.getTransferByID(transferId);
//    }

    @Override
    public Transfer getTransferById(long id) {
        return transferDao.getTransferByID(id);
    }
}


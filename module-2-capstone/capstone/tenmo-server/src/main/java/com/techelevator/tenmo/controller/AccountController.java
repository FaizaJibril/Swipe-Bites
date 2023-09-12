package com.techelevator.tenmo.controller;

<<<<<<< HEAD
//import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.service.Transfer;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
=======
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.service.AccountService;
>>>>>>> d645abbeb427ebe51e59560809a0eabfa7d908ae
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
//this is what requires a user to be authenticated to access endpoints in this controller
//it could be on a particular endpoint instead of the whole controller as well
@PreAuthorize("isAuthenticated()")
@CrossOrigin
@RequestMapping("/account")
public class AccountController extends BaseController {
private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    //private final AccountDao accountDAO;

//o add the necessary methods to handle transfers, viewing sent/received transfers, and retrieving transfer details.
/*
    @RequestMapping(path="/balance", method = RequestMethod.GET)
    public BigDecimal getAccountList(Principal principal){
        User sampleUser = super.getUserFromPrincipal(principal);
        Account userAccount = accountService.getAccountByUser(sampleUser);
        //this should return the actual balance

        //go after the accountDao and get the whole account object back

        //then return account.getBalance()
        return userAccount.getBalance();
    }

<<<<<<< HEAD

       private final TransferService transferService; // You'll need a TransferService to manage transfers

    public AccountController(TransferService transferService) {
        this.transferService = transferService;
    }

    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public BigDecimal getAccountBalance(Principal principal) {
        User user = super.getUserFromPrincipal(principal);
        return user.getBalance();
    }

    @RequestMapping(path = "/send-transfer", method = RequestMethod.POST)
    public ResponseEntity<String> sendTransfer(
            @RequestBody TransferRequest transferRequest,
            Principal principal
    ) {
        User sender = super.getUserFromPrincipal(principal);
        User receiver = transferService.findByUsername(transferRequest.getUserById());

        if (receiver == null) {
            return ResponseEntity.badRequest().body("Invalid receiver user ID.");
        }

        if (sender.getId().equals(receiver.getId())) {
            return ResponseEntity.badRequest().body("Cannot send money to yourself.");
        }

        BigDecimal amount = transferRequest.getBalance();
        if (amount.compareTo(BigDecimal.ZERO) <= 0 || amount.compareTo(sender.getBalance()) > 0) {
            return ResponseEntity.badRequest().body("Invalid transfer amount.");
        }

        transferService.create(username, password);

        return ResponseEntity.ok("Transfer created successfully.");
    }

    @RequestMapping(path = "/transfers", method = RequestMethod.GET)
    public List<Transfer> getTransfers(Principal principal) {
        User user = super.getUserFromPrincipal(principal);
        return transferService.getTransfersForUser(user);
    }

    @RequestMapping(path = "/transfer/{transferId}", method = RequestMethod.GET)
    public ResponseEntity<Transfer> getTransferDetails(
            @PathVariable Long transferId,
            Principal principal
    ) {
        User user = super.getUserFromPrincipal(principal);
        Transfer transfer = transferService.getTransferById(transferId);

        if (transfer == null || (!transfer.getSender().getId().equals(user.getId())
                && !transfer.getReceiver().getId().equals(user.getId()))) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(transfer);
    }
    /*
 */
=======
>>>>>>> d645abbeb427ebe51e59560809a0eabfa7d908ae
}





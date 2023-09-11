package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.service.InsufficientFundsException;
import com.techelevator.tenmo.service.TransferService;
import com.techelevator.tenmo.service.UserService;
import org.apache.coyote.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.techelevator.tenmo.model.User;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;


@RestController
@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class TransferController extends BaseController {

    private final TransferService transferService;
    private final UserService userService;

    public TransferController(TransferService transferService, UserService userService) {
        this.transferService = transferService;
        this.userService = userService;
    }

    @RequestMapping(path="/account/transfer", method = RequestMethod.GET)
    public List<Transfer> listForUser(Principal principal) {
        User user = super.getUserFromPrincipal(principal);
        return transferService.listTransferForUser(user);
    }

    @RequestMapping(path="/transfers/{id}", method = RequestMethod.GET)
    public Transfer getTransfer(Principal principal, @PathVariable long id) {
        User user = super.getUserFromPrincipal(principal);
        Transfer transfer = transferService.getTransferById(id);
        
        if (transfer == null || transfer.getUserFrom().getId() != user.getId()) {
            return null;
        }
        return transfer;
    }

    @RequestMapping(path="/users", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public Transfer sendTransfer(Principal principal, @RequestBody TransferDto transferDto) throws InsufficientFundsException {
        User user = super.getUserFromPrincipal(principal);

        //return transferService.getTransfer(user,id);

        Transfer transfer = transferService.sendTransfer(user, transferDto);
        return transfer;

    }
}
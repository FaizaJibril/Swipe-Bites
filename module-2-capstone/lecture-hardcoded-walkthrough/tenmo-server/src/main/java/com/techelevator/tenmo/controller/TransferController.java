package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.service.TransferService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@RestController
@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class TransferController extends BaseController{

    private final TransferService transferService;

    public TransferController(TransferService transferService){
        this.transferService = transferService;
    }

    @RequestMapping(path="/account/transfers",method = RequestMethod.GET)
    public List<Transfer> listForUser(Principal principal){
        User user = super.getUserFromPrincipal(principal);
        return transferService.listTransferForUser(user);
    }


    @RequestMapping(path="/transfers/{id}",method = RequestMethod.GET)
    public Transfer getTransfer(Principal principal, @PathVariable long id){
        User user = super.getUserFromPrincipal(principal);
        return new Transfer();
    }
}

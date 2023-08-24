package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
//this is what requires a user to be authenticated to access endpoints in this controller
//it could be on a particular endpoint instead of the whole controller as well
@PreAuthorize("isAuthenticated()")
@CrossOrigin
@RequestMapping("/account")
public class AccountController extends BaseController {

    @RequestMapping(path="/balance", method = RequestMethod.GET)
    public BigDecimal getAccountList(Principal principal){
        User sampleUser = super.getUserFromPrincipal(principal);
        //this should return the actual balance
        //go after the accountDao and get the whole account object back
        //then return account.getBalance()
        return BigDecimal.valueOf(999.99d);
    }
}

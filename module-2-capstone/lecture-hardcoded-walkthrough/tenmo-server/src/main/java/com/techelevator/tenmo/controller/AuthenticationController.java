package com.techelevator.tenmo.controller;

import javax.validation.Valid;

import com.techelevator.tenmo.service.UserService;
import com.techelevator.tenmo.model.LoginResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.techelevator.tenmo.model.LoginDto;
import com.techelevator.tenmo.model.RegisterUserDto;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller to authenticate users.
 */
@RestController
@CrossOrigin
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public LoginResponseDto login(@Valid @RequestBody LoginDto loginDto) {
        LoginResponseDto response = userService.login(loginDto);
        if (response != null) return response;
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username and password are incorrect.");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@Valid @RequestBody RegisterUserDto newUser) {
        if (!userService.createUser(newUser)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User registration failed.");
        }
    }

}


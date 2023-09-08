package com.techelevator.controller;

import javax.validation.Valid;

import com.techelevator.service.UserService;
import com.techelevator.model.LoginDto;
import com.techelevator.model.LoginResponseDto;
import com.techelevator.model.RegisterUserDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

/**
 * Controller to authenticate users.
 */

@CrossOrigin
@RestController
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


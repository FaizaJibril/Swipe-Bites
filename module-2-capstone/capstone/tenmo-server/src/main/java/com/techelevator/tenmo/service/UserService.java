package com.techelevator.tenmo.service;

import com.techelevator.tenmo.model.LoginDto;
import com.techelevator.tenmo.model.LoginResponseDto;
import com.techelevator.tenmo.model.RegisterUserDto;
import com.techelevator.tenmo.model.User;

import java.util.List;

public interface UserService {
    LoginResponseDto login(LoginDto loginDto);
    boolean createUser(RegisterUserDto newUser);
    List<User> getAllUsers(User user);
    User getCurrent(User user);

    List<User> getAllUsers();
}

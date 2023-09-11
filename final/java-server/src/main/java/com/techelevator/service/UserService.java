package com.techelevator.service;

import com.techelevator.model.LoginDto;
import com.techelevator.model.LoginResponseDto;
import com.techelevator.model.RegisterUserDto;
import com.techelevator.model.User;

import java.util.List;

public interface UserService {
    LoginResponseDto login(LoginDto loginDto);
    boolean createUser(RegisterUserDto newUser);
    List<User> getAllUsers(User user);
    User getCurrent(User user);
}

package com.techelevator.tenmo.service;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.LoginDto;
import com.techelevator.tenmo.model.LoginResponseDto;
import com.techelevator.tenmo.model.RegisterUserDto;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.security.jwt.TokenProvider;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserDao userDao;

    public UserServiceImpl(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder,
                           UserDao userDao) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDao = userDao;
    }

    @Override
    public LoginResponseDto login(LoginDto loginDto) {

        try {
            User user = userDao.findByUsername(loginDto.getUsername());

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.createToken(authentication, false, String.valueOf(user.getId()));

            return new LoginResponseDto(jwt, user);
        } catch (UsernameNotFoundException ex) {
            System.out.println("Username not found: " + loginDto.getUsername());
        } catch (AuthenticationException ex) {
            System.out.println("Authentication failed");
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean createUser(RegisterUserDto newUser) {
        try {
            if (userDao.create(newUser.getUsername(), newUser.getPassword())) {
                System.out.println("User created:" + newUser.getUsername());
                return true;
            }
            System.out.println("User could not be added to system:" + newUser.getUsername());
            return false;
        } catch (DuplicateKeyException ex) {
            System.out.println("Username already exists:" + newUser.getUsername());
            return false;
        } catch (Exception ex) {
            System.out.println("User could not be added to system:" + newUser.getUsername());
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> getAllUsers(User user) {
        return userDao.findAll();
    }

    @Override
    public User getCurrent(User user) {
        return userDao.getUserById(user.getId());
    }

}

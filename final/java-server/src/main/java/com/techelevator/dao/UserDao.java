package com.techelevator.dao;

import com.techelevator.model.RegisterUserDto;
import com.techelevator.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    User getUserById(long id);

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(RegisterUserDto newUser);

    List<User> getAllUsers();

    void createUser(User user);

    void updateUser(User user);

    void deleteUser(int id);

    void insertPreferences(String username, String preferences);
    void insertFullName(String username, String fullName);

}

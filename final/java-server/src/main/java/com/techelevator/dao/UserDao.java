package com.techelevator.dao;

import com.techelevator.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    User getUserById(long id);

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password);



    List<User> getAllUsers();

    void createUser(User user);

    void updateUser(User user);

    void deleteUser(int id);
    void insertPreferences(String username, String preferences);
    void insertFullName(String username, String fullName);
}

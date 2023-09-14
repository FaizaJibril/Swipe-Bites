package com.techelevator.dao;

import com.techelevator.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    User getUserById(long id);

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password);

<<<<<<< HEAD


=======
    List<User> getAllUsers();

    void createUser(User user);

    void updateUser(User user);

    void deleteUser(int id);
>>>>>>> b7cbf91103d966dd32be22713759acde09e07d6f
}

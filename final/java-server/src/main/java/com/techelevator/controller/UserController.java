package com.techelevator.controller;

import com.techelevator.dao.UserDao;
import com.techelevator.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//preferences, recommondations?

@RequestMapping("/user")
@CrossOrigin
public class UserController extends BaseController{

    private final UserDao userDao ;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userDao.getUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody User user) {
        userDao.createUser(user);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable int id, @RequestBody User user) {
        user.setId(id);
        userDao.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userDao.deleteUser(id);
    }
    @PostMapping("/insertFullName/{username}")
    public void insertFullName(@PathVariable String username, @RequestBody String fullName) {
        userDao.insertFullName(username, fullName);
    }

    @PostMapping("/insertPreferences/{username}")
    public void insertPreferences(@PathVariable String username, @RequestBody String preferences) {
        userDao.insertPreferences(username, preferences);
    }


}


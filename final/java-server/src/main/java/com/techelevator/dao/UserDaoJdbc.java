package com.techelevator.dao;

import com.techelevator.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoJdbc implements UserDao {

    private static final BigDecimal STARTING_BALANCE = new BigDecimal("1000.00");
    private final JdbcTemplate jdbcTemplate;

    public UserDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int findIdByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");

        int userId;
        try {
            userId = jdbcTemplate.queryForObject("select user_id from app_users where username = ?", int.class, username);
        } catch (NullPointerException | EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("User " + username + " was not found.");
        }

        return userId;
    }

    @Override
    public User getUserById(long userId) {
        String sql = "select user_id, username, password_hash from app_users where user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        if (results.next()) {
            return mapRowToUser(results,true, "");
        } else {
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "select user_id, username, password_hash from app_users";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            User user = mapRowToUser(results,true, "");
            users.add(user);
        }

        return users;
    }

    @Override
    public User findByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");

        String sql = "select user_id, username, password_hash from app_users where username = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
        if (rowSet.next()) {
            return mapRowToUser(rowSet,true, "");
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    @Override
    public boolean create(String username, String password) {

        // create user
        String sql = "insert into app_users (username, password_hash, role) values (?, ?, 'ROLE_USER') returning user_id";
        String password_hash = new BCryptPasswordEncoder().encode(password);
        Integer newUserId;
        newUserId = jdbcTemplate.queryForObject(sql, Integer.class, username, password_hash);

        if (newUserId == null) return false;

        return true;
    }


    private static User mapRowToUser(SqlRowSet rs, boolean includePassword, String ending) {
        User user = new User();
        if (ending.isEmpty()) {
            user.setId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
        } else {
            user.setId(rs.getInt("user_id" + ending));
            user.setUsername(rs.getString("username" + ending));
        }
        if (includePassword) {
            user.setPassword(rs.getString("password_hash"));
        }
        user.setActivated(true);
        user.setAuthorities("USER");
        return user;
    }

}

package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

public class Account {
    //balance, user_id, username, account_id, email, password
    private long user_id;
    private long account_id;
    private String username;
    private String email;

    private BigDecimal balance = BigDecimal.valueOf(0);
    @JsonIgnore //
    private String password;


    public Account(long user_id, long account_id, String username, String email, String password, BigDecimal balance) {
        this.user_id = user_id;
        this.account_id = account_id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.balance = balance;
    }

    public Account() {

    }

    public long getUser_id() {
        return user_id;
    }

    public long getAccount_id() {
        return account_id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public BigDecimal getBalance( ) {
        return this.balance;
    }

    public String getPassword() {
        return password;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

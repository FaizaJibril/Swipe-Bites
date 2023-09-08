package com.techelevator.controller;

import com.techelevator.model.User;
import com.techelevator.model.UserDetail;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.security.Principal;

public class BaseController {

    public User getUserFromPrincipal(Principal principal) {
        UserDetail userDetail = (UserDetail) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        StringBuilder builder = new StringBuilder();
        for (GrantedAuthority a: userDetail.getAuthorities()){
            builder.append(a.getAuthority()).append(",");
        }
        String auths = builder.toString();
        return new User(userDetail.getId(), userDetail.getUsername(), userDetail.getPassword(), auths);
    }
}

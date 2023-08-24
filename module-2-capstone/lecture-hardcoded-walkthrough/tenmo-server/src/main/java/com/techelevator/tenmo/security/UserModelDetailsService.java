package com.techelevator.tenmo.security;


import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserModelDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserModelDetailsService.class);

    private final UserDao userDao;

    public UserModelDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetail loadUserByUsername(final String login) {

        log.debug("Authenticating user '{}'", login);
        String lowercaseLogin = login.toLowerCase();
        return createSpringSecurityUser(lowercaseLogin, userDao.findByUsername(lowercaseLogin));
    }

    private UserDetail createSpringSecurityUser(String lowercaseLogin, User user) {
        if (!user.isActivated()) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
        return new UserDetail(user.getId(),user.getUsername(),
                user.getPassword(),
                grantedAuthorities);
    }
}


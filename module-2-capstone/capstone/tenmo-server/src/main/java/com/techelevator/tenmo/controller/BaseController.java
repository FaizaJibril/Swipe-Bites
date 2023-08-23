package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.exception.TransferException;
import com.techelevator.tenmo.model.ApiError;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserDetail;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.Principal;

public class BaseController {

//    private LogService logService;
//    public BaseController(LogService logService){
//        this.logService = logService;
//    }
    private User user;
    public User getUserFromPrincipal(Principal principal) {
        UserDetail userDetail = (UserDetail) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        StringBuilder builder = new StringBuilder();
        for (GrantedAuthority a: userDetail.getAuthorities()){
            builder.append(a.getAuthority()).append(",");
        }
        String auths = builder.toString();
        this.user = new User(userDetail.getId(), userDetail.getUsername(), userDetail.getPassword(), auths);
        return this.user;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({TransferException.class})
    public ApiError handleTransferException(Exception exception){
        //logService.logError(user,null,exception.getMessage(), exception);
        return new ApiError(HttpStatus.BAD_REQUEST,exception.getMessage(), exception);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class})
    public ApiError handleArgumentException(Exception exception){
        //logService.logError(user,null,exception.getMessage(), exception);
        return new ApiError(HttpStatus.BAD_REQUEST,"Bad request", exception);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({RuntimeException.class})
    public ApiError handleRuntimeException(Exception exception){
        //logService.logError(user,null,exception.getMessage(), exception);
        return new ApiError(HttpStatus.UNAUTHORIZED,"Bad request", exception);
    }

}

package com.userservice.controller;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.exception.AuthenticationException;
import com.userservice.ldap.service.UserService;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public String getUsername(@QueryParam("username") String username, @QueryParam("password") String password) throws Exception {

        System.out.println("Info: " + username + " | " + password);

        if(userService.authenticate(username, password)) {
            return "Access granted !";
        } else {
            throw new AuthenticationException("Authentication failed! Username or password invalid !");
        }
    }
}
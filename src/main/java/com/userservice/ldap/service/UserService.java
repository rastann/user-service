package com.userservice.ldap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userservice.ldap.repository.User;
import com.userservice.ldap.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Boolean authenticate(final String username, final String password) {
        List<User> user = userRepository.findBy(username, password);
        return user.size() == 1;
    }
}

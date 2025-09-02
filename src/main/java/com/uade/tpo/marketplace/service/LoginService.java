package com.uade.tpo.marketplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.marketplace.repository.UserRepository;
@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;


    public boolean login (String email, String password){
        return userRepository.findByEmail (email)
        .map(user -> user.getPassword().equals(password))
        .orElse(false);
    }
}

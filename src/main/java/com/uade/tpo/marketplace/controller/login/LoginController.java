package com.uade.tpo.marketplace.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.uade.tpo.marketplace.service.LoginService;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
        boolean success = loginService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if(success){
            return ResponseEntity.ok("Login Existoso");
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Los datos no coinciden");
        }

    }
}

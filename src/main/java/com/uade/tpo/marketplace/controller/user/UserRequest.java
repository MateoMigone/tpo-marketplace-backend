package com.uade.tpo.marketplace.controller.user;

import lombok.Data;

@Data
public class UserRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;

}

package com.uade.tpo.marketplace.controller.admin;

import com.uade.tpo.marketplace.entity.Role;
import lombok.Data;

@Data
public class AdminUserResponse {
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
}

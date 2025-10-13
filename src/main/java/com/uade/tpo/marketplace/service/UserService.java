package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.controller.admin.AdminUserResponse;
import com.uade.tpo.marketplace.controller.user.UserRequest;
import com.uade.tpo.marketplace.controller.user.UserResponse;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.exceptions.EmailException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserService {

    void cambiarRolUser(Long userId, String nuevoRolStr);
    UserResponse actualizarUser(String email, UserRequest request) throws EmailException;
    UserResponse getUserByEmail(String email);
    List<AdminUserResponse> getAllUsers();
}

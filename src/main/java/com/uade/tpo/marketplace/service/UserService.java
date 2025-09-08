package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.controller.user.UserRequest;
import com.uade.tpo.marketplace.entity.User;
import org.springframework.stereotype.Service;


@Service
public interface UserService {

    void cambiarRolUser(Long userId, String nuevoRolStr);
    User actualizarUser(String email, UserRequest request);
}

package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.controller.user.UserRequest;
import com.uade.tpo.marketplace.entity.Role;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void cambiarRolUser(Long userId, String nuevoRolStr) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Validar que el rol exista en el enum
        Role nuevoRol;
        try {
            nuevoRol = Role.valueOf(nuevoRolStr);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Rol inválido. Los roles válidos son: USER, ADMIN");
        }

        user.setRole(nuevoRol);
        userRepository.save(user);
    }

    //@Override
    public User actualizarUser(String email, UserRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return userRepository.save(user);
    }
}

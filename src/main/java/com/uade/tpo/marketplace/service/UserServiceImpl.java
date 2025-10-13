package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.controller.admin.AdminUserResponse;
import com.uade.tpo.marketplace.controller.user.UserRequest;
import com.uade.tpo.marketplace.controller.user.UserResponse;
import com.uade.tpo.marketplace.entity.Role;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.exceptions.EmailException;
import com.uade.tpo.marketplace.repository.UserRepository;
import com.uade.tpo.marketplace.utils.InfoValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
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

    @Transactional
    public UserResponse actualizarUser(String email, UserRequest request) throws EmailException {
        boolean isValidEmail = InfoValidator.isValidEmail(request.getEmail());
        if (!isValidEmail){
            throw new EmailException();
        }

        UserResponse userResponse = new UserResponse();
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
        if (passwordEncoder.matches(request.getPassword(),user.getPassword())){
            userRepository.save(user);
            userResponse.setEmail(request.getEmail());
            userResponse.setFirstName(request.getFirstName());
            userResponse.setLastName(request.getLastName());
        }

        return userResponse;
    }

    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User no encontrado: " + email));

        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(email);
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        return userResponse;
    }

    public List<AdminUserResponse> getAllUsers() {
        List<AdminUserResponse> usersResponse = new ArrayList<AdminUserResponse>();
        List<User> users = userRepository.findAll();

        for (User user : users) {
            AdminUserResponse adminUserResponse = new AdminUserResponse();
            adminUserResponse.setEmail(user.getEmail());
            adminUserResponse.setFirstName(user.getFirstName());
            adminUserResponse.setLastName(user.getLastName());
            adminUserResponse.setRole(user.getRole());
            usersResponse.add(adminUserResponse);
        }

        return usersResponse;
    }
}

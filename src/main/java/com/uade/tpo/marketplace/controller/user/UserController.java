package com.uade.tpo.marketplace.controller.user;

import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // PUT /api/v1/users/me
    @PutMapping("/me")
    public ResponseEntity<User> actualizarUser(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            @RequestBody UserRequest request) {

        User actualizado = userService.actualizarUser(userDetails.getUsername(), request);
        return ResponseEntity.ok(actualizado);
    }
}

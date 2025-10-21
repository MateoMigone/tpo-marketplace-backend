package com.uade.tpo.marketplace.controller.user;

import com.uade.tpo.marketplace.controller.auth.AuthenticationRequest;
import com.uade.tpo.marketplace.controller.auth.AuthenticationResponse;
import com.uade.tpo.marketplace.entity.Game;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.exceptions.EmailException;
import com.uade.tpo.marketplace.service.AuthenticationService;
import com.uade.tpo.marketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    // PUT /api/v1/users/me
    @PutMapping("/me")
    public ResponseEntity<?> actualizarUser(
            Authentication auth,
            @RequestBody UserRequest request) throws EmailException {

        UserResponse actualizado = userService.actualizarUser(auth.getName(), request);

        // If the user updated the password, return a new auth token so the client can continue
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            AuthenticationRequest authenticationRequest = new AuthenticationRequest(actualizado.getEmail(), request.getPassword());
            AuthenticationResponse authResp = authenticationService.authenticate(authenticationRequest);
            return ResponseEntity.ok(authResp);
        }

        // No password change — return 204 No Content to indicate success without a token
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUserByEmail(Authentication auth) {
        UserResponse user = userService.getUserByEmail(auth.getName());
        return ResponseEntity.ok(user);
    }
}

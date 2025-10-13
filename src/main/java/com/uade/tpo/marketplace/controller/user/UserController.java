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
    public ResponseEntity<AuthenticationResponse> actualizarUser(
            Authentication auth,
            @RequestBody UserRequest request) throws EmailException {

        UserResponse actualizado = userService.actualizarUser(auth.getName(), request);
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(actualizado.getEmail(), request.getPassword());

        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUserByEmail(Authentication auth) {
        UserResponse user = userService.getUserByEmail(auth.getName());
        return ResponseEntity.ok(user);
    }
}

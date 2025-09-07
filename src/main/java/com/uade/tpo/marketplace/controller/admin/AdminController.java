package com.uade.tpo.marketplace.controller.admin;

import com.uade.tpo.marketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    // Solo usuarios con rol ADMIN pueden acceder
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/usuarios/{userId}/rol")
    public ResponseEntity<String> cambiarRol(
            @PathVariable Long userId,
            @RequestParam String nuevoRol) {

        userService.cambiarRolUser(userId, nuevoRol.toUpperCase());
        return ResponseEntity.ok("Rol actualizado correctamente");
    }
}

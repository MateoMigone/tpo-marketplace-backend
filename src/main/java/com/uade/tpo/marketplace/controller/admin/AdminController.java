package com.uade.tpo.marketplace.controller.admin;

import com.uade.tpo.marketplace.service.UserService;
import com.uade.tpo.marketplace.service.OrderService; // <-- NUEVO IMPORT
import com.uade.tpo.marketplace.entity.Order; // Importar la entidad Order
import com.uade.tpo.marketplace.controller.admin.AdminOrderResponse; // <-- NUEVO IMPORT
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors; // Necesario para el .stream().map()
import java.time.ZoneId; // Necesario si usas ZoneId en LocalDateTime
import java.math.BigDecimal; // Importa BigDecimal si es el tipo de TotalPrice


@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')") // <-- Se recomienda mover la protección al nivel de clase
public class AdminController {

    private final UserService userService;
    private final OrderService orderService; // <-- NUEVA DEPENDENCIA

    // La protección @PreAuthorize ahora se puede omitir en los métodos si está a nivel de clase
    
    @PutMapping("/usuarios/{userId}/rol")
    public ResponseEntity<String> cambiarRol(
            @PathVariable Long userId,
            @RequestBody ChangeRoleRequest changeRoleRequest) {

        userService.cambiarRolUser(userId, changeRoleRequest.getNuevoRol().toUpperCase());
        return ResponseEntity.ok("Rol actualizado correctamente");
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<AdminUserResponse>> getAllUsers() {
        List<AdminUserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // ----------------------------------------------------------------------
    // NUEVO ENDPOINT PARA ÓRDENES
    // ----------------------------------------------------------------------

    @GetMapping("/orders")
    public ResponseEntity<List<AdminOrderResponse>> getAllOrders() {
        
        // 1. Obtiene las entidades de órdenes
        List<Order> orders = orderService.findAllOrders();
        
        // 2. Mapea las entidades a DTOs (AdminOrderResponse)
        List<AdminOrderResponse> response = orders.stream()
            .map(this::mapToAdminOrderResponse)
            .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
    
    // Método Auxiliar de Mapeo
    private AdminOrderResponse mapToAdminOrderResponse(Order order) {
        // Asumiendo que Order tiene un campo totalPrice de tipo Double/BigDecimal 
        // y un campo date de tipo LocalDateTime

        String userEmail = (order.getUser() != null) 
                           ? order.getUser().getEmail() 
                           : "Usuario Eliminado";

        return AdminOrderResponse.builder()
            .id(order.getId())
            .userEmail(userEmail)
            // Asegúrate de que totalPrice sea compatible con BigDecimal si lo usas
            .totalAmount(BigDecimal.valueOf(order.getTotalPrice())) 
            .creationDate(order.getDate())
            // Asumo que tu entidad Order tiene un método getStatus() que devuelve un Enum
            // Si no usas Status, simplemente elimina esta línea o usa un valor fijo
            // .status(order.getStatus().name()) 
            .build();
    }
}

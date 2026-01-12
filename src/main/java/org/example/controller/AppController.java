package org.example.controller;

import org.example.dto.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AppController {


    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SEMI_ADMIN')")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(new MessageResponse("User Management Module"));
    }


    @GetMapping("/settings")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSettings() {
        return ResponseEntity.ok(new MessageResponse("System Settings Module"));
    }

    @GetMapping("/reports")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SEMI_ADMIN')")
    public ResponseEntity<?> getReports() {
        return ResponseEntity.ok(new MessageResponse("Reports Module"));
    }

    @GetMapping("/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getRoles() {
        return ResponseEntity.ok(new MessageResponse("Role Management Module"));
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SEMI_ADMIN') or hasRole('USER')")
    public ResponseEntity<?> getProfile() {
        return ResponseEntity.ok(new MessageResponse("Profile Module"));
    }


    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SEMI_ADMIN') or hasRole('USER')")
    public ResponseEntity<?> getDashboard() {
        return ResponseEntity.ok(new MessageResponse("Dashboard Module"));
    }
}

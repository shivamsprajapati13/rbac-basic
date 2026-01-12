package org.example.controller;

import org.example.dto.ModulesResponse;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('SEMI_ADMIN') or hasRole('USER')")
    public ResponseEntity<?> getModules() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String role = user.getRoles().iterator().next().getName();
        List<String> modules = getModulesByRole(role);

        return ResponseEntity.ok(new ModulesResponse(role, modules));
    }

    private List<String> getModulesByRole(String role) {
        if ("ADMIN".equals(role)) {
            return Arrays.asList(
                "User Management",
                "System Settings",
                "Reports",
                "Role Management",
                "Profile",
                "Dashboard"
            );
        } else if ("SEMI_ADMIN".equals(role)) {
            return Arrays.asList(
                "Limited User Management",
                "Limited Reports",
                "Profile",
                "Dashboard"
            );
        } else if ("USER".equals(role)) {
            return Arrays.asList(
                "Profile",
                "Dashboard"
            );
        }
        return Collections.emptyList();
    }
}

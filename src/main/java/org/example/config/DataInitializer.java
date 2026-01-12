package org.example.config;

import org.example.model.Role;
import org.example.model.User;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Create roles
        createRoleIfNotExists("ADMIN", "Administrator");
        createRoleIfNotExists("SEMI_ADMIN", "Semi-Administrator");
        createRoleIfNotExists("USER", "User");

        // Create default users with their credentials
        createUserIfNotExists("admin", "admin@example.com", "admin123", "ADMIN");
        createUserIfNotExists("semiadmin", "semiadmin@example.com", "semiadmin123", "SEMI_ADMIN");
        createUserIfNotExists("user", "user@example.com", "user123", "USER");
    }

    private void createRoleIfNotExists(String name, String description) {
        if (roleRepository.findByName(name).isEmpty()) {
            Role role = new Role();
            role.setName(name);
            role.setDescription(description);
            roleRepository.save(role);
        }
    }

    private void createUserIfNotExists(String username, String email, String password, String roleName) {
        if (!userRepository.existsByUsername(username)) {
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setEnabled(true);
            user.setRoles(Set.of(roleRepository.findByName(roleName).get()));
            userRepository.save(user);
        }
    }
}

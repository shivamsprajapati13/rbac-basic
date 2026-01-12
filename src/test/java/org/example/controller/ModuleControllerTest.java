package org.example.controller;

import org.example.model.Role;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ModuleController.class)
public class ModuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "admin")
    public void testGetModulesForAdmin() throws Exception {
        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        
        User admin = new User();
        admin.setUsername("admin");
        admin.setRoles(Set.of(adminRole));

        when(userRepository.findByUsername("admin")).thenReturn(java.util.Optional.of(admin));

        mockMvc.perform(get("/api/modules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("ADMIN"))
                .andExpect(jsonPath("$.modules").isArray())
                .andExpect(jsonPath("$.modules.length()").value(6));
    }

    @Test
    @WithMockUser(username = "user")
    public void testGetModulesForUser() throws Exception {
        Role userRole = new Role();
        userRole.setName("USER");
        
        User user = new User();
        user.setUsername("user");
        user.setRoles(Set.of(userRole));

        when(userRepository.findByUsername("user")).thenReturn(java.util.Optional.of(user));

        mockMvc.perform(get("/api/modules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("USER"))
                .andExpect(jsonPath("$.modules.length()").value(2));
    }
}

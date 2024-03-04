package com.qaroni.library.application.adapters.gateway;

import com.qaroni.library.application.adapters.repository.UserRepository;
import com.qaroni.library.application.adapters.repository.dto.SignUpDto;
import com.qaroni.library.application.adapters.repository.entity.Role;
import com.qaroni.library.application.adapters.repository.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {UserController.class})
@ComponentScan(basePackages = {"com.qaroni"})
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @Test
    @DisplayName("When calling the endpoint POST /api/private/signup it should return status Created")
    void registerUser() throws Exception {
        User user = User.builder()
                .id(1L)
                .email("testuser@testmail.com")
                .fullName("Test User")
                .password("testpassword")
                .role(Role.LIBRARIAN)
                .build();

        when(userRepository.save(user)).thenReturn(user);

        mockMvc.perform(post("/api/public/signup").contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "full_name": "Test User",
                            "email": "testuser@testmail.com",
                            "password": "testpassword",
                            "role": "LIBRARIAN"
                        }""")).andExpect(status().isCreated());
    }
}
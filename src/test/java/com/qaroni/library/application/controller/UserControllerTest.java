package com.qaroni.library.application.controller;

import com.qaroni.library.application.entity.Role;
import com.qaroni.library.application.entity.User;
import com.qaroni.library.application.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService service;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .fullName("User 1")
                .email("user1@email.com")
                .password("12345")
                .role(Role.LIBRARIAN).build();
    }

    @Test
    void getAll() throws Exception {
        when(service.findAllUsers()).thenReturn(List.of(user));
        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value(user.getFullName()));
    }

    @Test
    void getById() throws Exception {
        when(service.findUserById(1L)).thenReturn(user);
        mockMvc.perform(get("/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value(user.getFullName()));
    }

    @Test
    void create() throws Exception {
        User postUser = User.builder()
                .id(1L)
                .fullName("User 1")
                .email("user1@email.com")
                .password("12345")
                .role(Role.LIBRARIAN).build();
        when(service.saveUser(postUser)).thenReturn(user);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "fullName": "User 1",
                            "email": "user1@email.com",
                            "password": "12345",
                            "role": "LIBRARIAN"
                        }"""))
                .andExpect(status().isOk());
    }
}
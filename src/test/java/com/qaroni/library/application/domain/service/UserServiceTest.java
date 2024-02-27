package com.qaroni.library.application.domain.service;

import com.qaroni.library.application.domain.entity.Role;
import com.qaroni.library.application.domain.entity.User;
import com.qaroni.library.application.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService service;

    @MockBean
    private UserRepository repository;

    private User user;

    @BeforeEach
    void setUp()
    {
        user = User.builder()
                .id(1L)
                .fullName("User 1")
                .email("user1@email.com")
                .password("12345")
                .role(Role.LIBRARIAN)
                .build();
    }

    @Test
    void findAllUsers() {
        when(repository.findAll()).thenReturn(List.of(user));
        List<User> userList = service.findAllUsers();
        assertEquals(userList.size(), 1);
    }

    @Test
    void findUserById() {
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        User requestedUser = service.findUserById(1L);
        assertEquals(user.getFullName(), requestedUser.getFullName());
    }

    @Test
    void saveUser() {
        when(repository.save(user)).thenReturn(user);
        User savedUser = service.saveUser(user);
        assertEquals(user.getFullName(), savedUser.getFullName());
    }
}
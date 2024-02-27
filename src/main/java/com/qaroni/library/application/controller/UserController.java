package com.qaroni.library.application.controller;

import com.qaroni.library.application.entity.User;
import com.qaroni.library.application.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService service;

    @GetMapping("/users")
    List<User> getAll() {
        return service.findAllUsers();
    }

    @GetMapping("/users/{id}")
    User getById(@PathVariable Long id) {
        return service.findUserById(id);
    }

    @PostMapping("/users")
    public User create(@Valid @RequestBody User user) {
        return service.saveUser(user);
    }

    @PutMapping("/users/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        return service.updateUser(id, user);
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteUser(id);
    }
}

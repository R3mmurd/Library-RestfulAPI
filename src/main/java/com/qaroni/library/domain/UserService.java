package com.qaroni.library.domain;

import com.qaroni.library.application.adapters.repository.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    User findUserById(Long id);

    User saveUser(User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);
}

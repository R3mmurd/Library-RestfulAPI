package com.qaroni.library.application.service;

import com.qaroni.library.application.entity.User;
import com.qaroni.library.application.exceptions.UserNotFoundException;
import com.qaroni.library.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository repository;

    @Override
    public List<User> findAllUsers() {
        return repository.findAll();
    }

    @Override
    public User findUserById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User saveUser(User user) {
        return repository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        User currentUser = this.findUserById(id);
        currentUser.from(user);
        return repository.save(currentUser);
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}

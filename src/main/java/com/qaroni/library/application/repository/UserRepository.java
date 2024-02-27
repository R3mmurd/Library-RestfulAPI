package com.qaroni.library.application.repository;

import com.qaroni.library.application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

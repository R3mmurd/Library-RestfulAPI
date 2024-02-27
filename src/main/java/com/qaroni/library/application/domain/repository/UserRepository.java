package com.qaroni.library.application.domain.repository;

import com.qaroni.library.application.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

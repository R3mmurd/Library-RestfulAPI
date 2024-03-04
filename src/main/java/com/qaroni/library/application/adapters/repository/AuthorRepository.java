package com.qaroni.library.application.adapters.repository;

import com.qaroni.library.application.adapters.repository.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    boolean existsByDocumentId(String fullName);
}

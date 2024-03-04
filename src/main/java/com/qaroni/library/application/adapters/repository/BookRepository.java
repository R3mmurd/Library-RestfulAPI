package com.qaroni.library.application.adapters.repository;

import com.qaroni.library.application.adapters.repository.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);
}

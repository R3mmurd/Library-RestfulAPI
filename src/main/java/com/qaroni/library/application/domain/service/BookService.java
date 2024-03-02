package com.qaroni.library.application.domain.service;

import com.qaroni.library.application.domain.dto.BookDto;
import com.qaroni.library.application.domain.dto.CreateBookDto;
import com.qaroni.library.application.domain.entity.Author;
import com.qaroni.library.application.domain.entity.Book;
import com.qaroni.library.application.domain.exceptions.APIException;
import com.qaroni.library.application.domain.repository.AuthorRepository;
import com.qaroni.library.application.domain.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;

    @Autowired
    private AuthorRepository authorRepository;

    public List<Book> findAll() {
        return repository.findAll();
    }

    public List<Book> findByAuthorId(Long authorId) {
        Author author = authorRepository
                .findById(authorId)
                .orElseThrow(() -> new APIException("Could not find author " + authorId, HttpStatus.NOT_FOUND));
        return author.getBooks();
    }

    public Book findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new APIException("Could not find book " + id, HttpStatus.NOT_FOUND));
    }

    public Book save(CreateBookDto bookDto) {
        if (repository.existsByIsbn(bookDto.getIsbn())) {
            throw new APIException("ISBN " + bookDto.getIsbn() + " already exists!", HttpStatus.BAD_REQUEST);
        }

        Book book = bookDto.toEntity();
        book.setAuthors(bookDto.getAuthorIds().stream().map(id -> authorRepository.findById(id).orElseThrow(() -> new APIException("Could not find author " + id, HttpStatus.NOT_FOUND))).toList());
        return repository.save(book);
    }

    public Book update(BookDto bookDto, Long id) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new APIException("Could not find book " + id, HttpStatus.NOT_FOUND));

        book.setIsbn(bookDto.getIsbn());
        book.setTitle(bookDto.getTitle());
        return repository.save(book);
    }

    public Long numBooks() {
        return repository.count();
    }
}

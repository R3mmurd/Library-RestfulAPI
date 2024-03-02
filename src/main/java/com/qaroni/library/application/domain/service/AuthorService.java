package com.qaroni.library.application.domain.service;

import com.qaroni.library.application.domain.dto.AuthorDto;
import com.qaroni.library.application.domain.entity.Author;
import com.qaroni.library.application.domain.exceptions.APIException;
import com.qaroni.library.application.domain.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository repository;

    public List<Author> findAll() {
        return repository.findAll();
    }

    public Author findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new APIException("Could not find author " + id, HttpStatus.NOT_FOUND));
    }

    public Author save(AuthorDto authorDto) {
        if (repository.existsByDocumentId(authorDto.getDocumentId())) {
            throw new APIException("Author " + authorDto.getFullName() + " already exists!", HttpStatus.BAD_REQUEST);
        }

        return repository.save(authorDto.toEntity());
    }

    public Author update(AuthorDto authorDto, Long id) {
        Author author = repository.findById(id)
                .orElseThrow(() -> new APIException("Could not find author " + id, HttpStatus.NOT_FOUND));

        author.setDocumentId(authorDto.getDocumentId());
        author.setFullName(authorDto.getFullName());
        return repository.save(author);
    }
}

package com.qaroni.library.application.domain.controller;

import com.qaroni.library.application.domain.dto.BookDto;
import com.qaroni.library.application.domain.dto.CreateBookDto;
import com.qaroni.library.application.domain.dto.FullAuthorDto;
import com.qaroni.library.application.domain.dto.FullBookDto;
import com.qaroni.library.application.domain.service.BookService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {
    @Autowired
    private BookService service;

    @Tag(name = "List Books", description = "Retrieves a list with all books")
    @ApiResponse(responseCode = "200",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = FullBookDto.class)) })
    @GetMapping("/public/books")
    public ResponseEntity<List<FullBookDto>> findAll() {
        return new ResponseEntity<>(service.findAll().stream().map(FullBookDto::fromEntity).toList(), HttpStatus.OK);
    }

    @Tag(name = "Get Book", description = "Retrieves the book with the given id")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FullBookDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content)
    })
    @GetMapping("/public/books/{id}")
    public ResponseEntity<FullBookDto> findById(@Parameter(
            description = "ID of book to be retrieved",
            required = true) @PathVariable Long id) {
        return new ResponseEntity<>(FullBookDto.fromEntity(service.findById(id)), HttpStatus.OK);
    }

    @Tag(name = "Get Books by Author", description = "Retrieves the list of by the given author id")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FullBookDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Author not found",
                    content = @Content)
    })
    @GetMapping("/public/books/by/{authorId}")
    public ResponseEntity<List<FullBookDto>> findByAuthorId(@Parameter(
            description = "ID of author to be retrieved",
            required = true) @PathVariable Long authorId) {
        return new ResponseEntity<>(service.findByAuthorId(authorId).stream().map(FullBookDto::fromEntity).toList(), HttpStatus.OK);
    }

    @Tag(name = "Create Book", description = "Creates a new book")
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FullBookDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Book already exists",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "User's role is not allowed to perform this operation",
                    content = @Content)
    })
    @RolesAllowed({"EXECUTIVE", "LIBRARIAN"})
    @PostMapping("/private/books")
    public ResponseEntity<FullBookDto> save(@Valid @RequestBody CreateBookDto bookDto) {
        return new ResponseEntity<>(FullBookDto.fromEntity(service.save(bookDto)), HttpStatus.CREATED);
    }
    @Tag(name = "Update Book", description = "Updates the book with the given id")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FullAuthorDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "User's role is not allowed to perform this operation",
                    content = @Content)
    })
    @RolesAllowed({"EXECUTIVE", "LIBRARIAN"})
    @PutMapping("/private/books/{id}")
    public ResponseEntity<FullBookDto> update(@Valid @RequestBody BookDto bookDto,
                                              @Parameter(
                                                      description = "ID of book to be updated",
                                                      required = true)
                                              @PathVariable Long id) {
        return new ResponseEntity<>(FullBookDto.fromEntity(service.update(bookDto, id)), HttpStatus.OK);
    }
}
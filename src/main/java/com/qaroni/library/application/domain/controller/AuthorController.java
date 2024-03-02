package com.qaroni.library.application.domain.controller;

import com.qaroni.library.application.domain.dto.AuthorDto;
import com.qaroni.library.application.domain.dto.FullAuthorDto;
import com.qaroni.library.application.domain.entity.Author;
import com.qaroni.library.application.domain.service.AuthorService;
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
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class AuthorController {
    @Autowired
    AuthorService service;

    @Tag(name = "List Authors", description = "Retrieves a list with all authors")
    @ApiResponse(responseCode = "200",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = FullAuthorDto.class)) })
    @GetMapping("/public/authors")
    public ResponseEntity<List<FullAuthorDto>> findAll() {
        return new ResponseEntity<>(service.findAll().stream().map(FullAuthorDto::fromEntity).toList(), HttpStatus.OK);
    }

    @Tag(name = "Get Author", description = "Retrieves the author with the given id")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FullAuthorDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Author not found",
                    content = @Content)
    })
    @GetMapping("/public/authors/{id}")
    public ResponseEntity<FullAuthorDto> findById(@Parameter(
            description = "ID of author to be retrieved",
            required = true) @PathVariable Long id) {
        return new ResponseEntity<>(FullAuthorDto.fromEntity(service.findById(id)), HttpStatus.OK);
    }

    @Tag(name = "Create Author", description = "Creates a new author")
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FullAuthorDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Author already exists",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "User's role is not allowed to perform this operation",
                    content = @Content)
    })
    @RolesAllowed({"EXECUTIVE", "LIBRARIAN"})
    @PostMapping("/private/authors")
    public ResponseEntity<FullAuthorDto> save(@Valid @RequestBody AuthorDto authorDto) {
        return new ResponseEntity<>(FullAuthorDto.fromEntity(service.save(authorDto)), HttpStatus.CREATED);
    }

    @Tag(name = "Update Author", description = "Updates the author with the given id")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FullAuthorDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Author not found",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "User's role is not allowed to perform this operation",
                    content = @Content)
    })
    @RolesAllowed({"EXECUTIVE", "LIBRARIAN"})
    @PutMapping("/private/authors/{id}")
    public ResponseEntity<FullAuthorDto> update(@Valid @RequestBody AuthorDto authorDto,
                                                @Parameter(
                                                        description = "ID of author to be updated",
                                                        required = true)
                                                @PathVariable Long id) {
        return new ResponseEntity<>(FullAuthorDto.fromEntity(service.update(authorDto, id)), HttpStatus.OK);
    }

}

package com.qaroni.library.application.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qaroni.library.application.domain.entity.Author;
import com.qaroni.library.application.domain.entity.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CreateBookDto {
    @JsonProperty("isbn")
    @NotBlank(message = "Please, set the isbn")
    @NotNull
    private String isbn;

    @JsonProperty("title")
    @NotBlank(message = "Please, set the title")
    private String title;

    @JsonProperty("authors")
    @NotEmpty(message = "Please, set the authors")
    private List<Long> authorIds;

    public Book toEntity() {
        return Book.builder()
                .isbn(this.isbn)
                .title(this.title)
                .build();
    }
}

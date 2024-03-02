package com.qaroni.library.application.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qaroni.library.application.domain.entity.Book;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.Set;

@Data
@Builder
public class BookDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("isbn")
    @NotBlank(message = "Please, set the isbn")
    private String isbn;

    @JsonProperty("title")
    @NotBlank(message = "Please, set the author name")
    private String title;

    public Book toEntity() {
        return Book.builder()
                .isbn(this.isbn)
                .title(this.title)
                .authors(Collections.emptyList())
                .build();
    }

    public static BookDto fromEntity(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .build();
    }
}

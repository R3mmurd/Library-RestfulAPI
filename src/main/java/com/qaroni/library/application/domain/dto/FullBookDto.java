package com.qaroni.library.application.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qaroni.library.application.domain.entity.Author;
import com.qaroni.library.application.domain.entity.Book;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FullBookDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("isbn")
    @NotBlank(message = "Please, set the isbn")
    private String isbn;

    @JsonProperty("title")
    @NotBlank(message = "Please, set the author name")
    private String title;

    private List<AuthorDto> authors;

    public Book toEntity() {
        return Book.builder()
                .isbn(this.isbn)
                .title(this.title)
                .authors(this.authors.stream().map(AuthorDto::toEntity).toList())
                .build();
    }

    public static FullBookDto fromEntity(Book book) {
        return FullBookDto.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .authors(book.getAuthors().stream().map(AuthorDto::fromEntity).toList())
                .build();
    }
}

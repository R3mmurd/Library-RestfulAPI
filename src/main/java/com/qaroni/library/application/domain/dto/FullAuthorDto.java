package com.qaroni.library.application.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qaroni.library.application.domain.entity.Author;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FullAuthorDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("document_id")
    @NotBlank(message = "Please, set the document id")
    private String documentId;

    @JsonProperty("full_name")
    @NotBlank(message = "Please, set the author name")
    private String fullName;

    private List<BookDto> books;

    public Author toEntity() {
        return Author.builder()
                .documentId(this.documentId)
                .fullName(this.fullName)
                .books(this.books.stream().map(BookDto::toEntity).toList())
                .build();
    }

    public static FullAuthorDto fromEntity(Author author) {
        return FullAuthorDto.builder()
                .id(author.getId())
                .documentId(author.getDocumentId())
                .fullName(author.getFullName())
                .books(author.getBooks().stream().map(BookDto::fromEntity).toList())
                .build();
    }
}

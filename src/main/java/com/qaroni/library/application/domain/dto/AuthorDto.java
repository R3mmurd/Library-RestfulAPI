package com.qaroni.library.application.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qaroni.library.application.domain.entity.Author;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@Builder
public class AuthorDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("document_id")
    @NotBlank(message = "Please, set the document id")
    private String documentId;

    @JsonProperty("full_name")
    @NotBlank(message = "Please, set the author name")
    private String fullName;

    public Author toEntity() {
        return Author.builder()
                .documentId(this.documentId)
                .fullName(this.fullName)
                .books(Collections.emptyList())
                .build();
    }

    public static AuthorDto fromEntity(Author author) {
        return AuthorDto.builder()
                .id(author.getId())
                .documentId(author.getDocumentId())
                .fullName(author.getFullName())
                .build();
    }
}

package com.qaroni.library.application.adapters.repository.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
    @JsonProperty("message")
    private String message;

    @JsonProperty("token")
    private String token;
}

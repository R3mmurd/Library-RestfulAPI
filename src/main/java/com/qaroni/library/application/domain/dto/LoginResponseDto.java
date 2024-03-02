package com.qaroni.library.application.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    @JsonProperty("message")
    private String message;

    @JsonProperty("token")
    private String token;
}

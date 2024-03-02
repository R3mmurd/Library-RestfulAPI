package com.qaroni.library.application.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
    @JsonProperty("email")
    @NotBlank(message = "Please set the email")
    private String email;

    @JsonProperty("password")
    @NotBlank(message = "Please set the password")
    private String password;
}

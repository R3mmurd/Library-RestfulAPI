package com.qaroni.library.application.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
    @NotBlank(message = "Please set the email")
    private String email;

    @NotBlank(message = "Please set the password")
    private String password;
}

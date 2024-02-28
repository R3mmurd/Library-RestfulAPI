package com.qaroni.library.application.domain.dto;

import com.qaroni.library.application.domain.entity.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpDto {
    @NotBlank(message = "Please set the full name")
    private String fullName;

    @NotBlank(message = "Please set the email")
    private String email;

    @NotBlank(message = "Please set the password")
    private String password;

    @NotBlank(message = "Please set the role name")
    private Role role;
}

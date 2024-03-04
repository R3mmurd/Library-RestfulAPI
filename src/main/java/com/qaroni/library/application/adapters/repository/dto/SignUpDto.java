package com.qaroni.library.application.adapters.repository.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qaroni.library.application.adapters.repository.entity.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpDto {
    @JsonProperty("full_name")
    @NotBlank(message = "Please set the full name")
    private String fullName;

    @JsonProperty("email")
    @NotBlank(message = "Please set the email")
    private String email;

    @JsonProperty("password")
    @NotBlank(message = "Please set the password")
    private String password;

    @JsonProperty("role")
    @NotBlank(message = "Please set the role name")
    private Role role;
}

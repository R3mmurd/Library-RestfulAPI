package com.qaroni.library.application.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please set the full name")
    private String fullName;

    @NotBlank(message = "Please set the email")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Please set the password")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void from(User user) {
        if (Objects.nonNull(user.fullName) && !"".equalsIgnoreCase(user.fullName)) {
            this.fullName = user.fullName;
        }

        if (Objects.nonNull(user.email) && !"".equalsIgnoreCase(user.email)) {
            this.email = user.email;
        }

        if (Objects.nonNull(user.password) && !"".equalsIgnoreCase(user.password)) {
            this.password = user.password;
        }

        if (Objects.nonNull(user.role)) {
            this.role = user.role;
        }
    }
}

package com.qaroni.library.application.domain.entity;

import jakarta.persistence.*;
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

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
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

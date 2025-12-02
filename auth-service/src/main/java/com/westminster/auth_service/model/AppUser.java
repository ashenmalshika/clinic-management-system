package com.westminster.auth_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // bcrypt

    private String roles; // e.g., ROLE_ADMIN,ROLE_RECEPTIONIST

    // getters & setters
}

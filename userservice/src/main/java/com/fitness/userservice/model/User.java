package com.fitness.userservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.Data;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "users")
@Data
public class User {
    // Set primary key to be a UUID string
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private String id;

    // Set email to be unique and not null
    @Column(unique = true, nullable = false)
    private String email;

    // Set password to be not null
    @Column(nullable = false)
    private String password;

    private String firstName;
    private String lastName;

    // Set role to be an enum with default value of USER
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    // Set createdAt and updatedAt to be automatically generated timestamps
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

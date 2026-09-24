package com.fitness.userservice.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.validation.constraints.Email;
/**
 * RegisterRequest
 */

@Data
public class RegisterRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format") 
    private String email;

    @NotBlank (message = "Password is required")
    @Size (min = 8, message = "Password must be at least 8 characters long")    
    private String password;
    
    private String firstName;
    private String lastName;
}

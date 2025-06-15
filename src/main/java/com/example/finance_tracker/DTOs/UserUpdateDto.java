package com.example.finance_tracker.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateDto {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email(message = "Email must be valid")
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8, message = "Password must contain at least 8 characters")
    private String password;
}

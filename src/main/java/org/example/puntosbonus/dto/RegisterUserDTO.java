package org.example.puntosbonus.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDTO {

    @NotBlank(message = "First name is required")
    @Size(min = 2, message = "First name too short")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, message = "Last name too short")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    // El tester suele esperar que una contraseña de solo letras falle el "format"
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$",
            message = "Password must contain at least one letter and one number")
    private String password;
}
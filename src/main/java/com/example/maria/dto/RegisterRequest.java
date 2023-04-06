package com.example.maria.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@Builder
@AllArgsConstructor
@Validated
@NoArgsConstructor
public class RegisterRequest {
    @Size(min = 3,max = 12, message = "Invalid!First Name must be between 3 - 12 characters ")
    private String firstName;
    @Size(min = 3,max = 12, message = "Invalid!Last Name must be between 3 - 12 characters ")
    private String lastName;
    @Size(min = 3,max = 12, message = "Invalid!email must be between 3 - 12 characters ")
    @Email
    private String email;
    @Size(min = 3,max = 12, message = "Invalid!Password must be between 3 - 12 characters ")
    private String password;

}

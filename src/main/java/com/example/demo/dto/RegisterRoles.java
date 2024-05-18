package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRoles {
    private RegistrationRequest registrationRequest;
    private String role;
    private String secretKey;
}

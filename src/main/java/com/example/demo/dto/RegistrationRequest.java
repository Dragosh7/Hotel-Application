package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegistrationRequest {
    private String username;
    private String password;
}


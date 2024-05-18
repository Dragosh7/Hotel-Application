package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ReservationResponse {
    private Long id;
    private String username;
    private String hotel;
    private Integer roomNumber;
    private Integer type;
    private Double price;
    private LocalDateTime date;
    private String status;
}

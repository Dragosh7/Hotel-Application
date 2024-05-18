package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponseDto {
    private Integer roomNumber;
    private Integer type;
    private Double price;
    private boolean isAvailable;
}


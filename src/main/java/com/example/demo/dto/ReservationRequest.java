package com.example.demo.dto;

import com.example.demo.entity.Room;
import com.example.demo.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ReservationRequest {

    private String username;
    private Integer roomNumber;
    private Integer type;
    private Double price;
    private LocalDateTime date;
}

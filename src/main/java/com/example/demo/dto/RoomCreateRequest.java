package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomCreateRequest {
    private Long hotelId;
    private Integer roomNumber;
    private Integer type;
    private Double price;
    @JsonProperty("isAvailable")
    private boolean isAvailable=true;
}

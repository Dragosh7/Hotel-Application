package com.example.demo.controller;

import com.example.demo.dto.HotelDto;
import com.example.demo.dto.RoomResponseDto;
import com.example.demo.entity.Hotel;
import com.example.demo.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        Hotel createdHotel = hotelService.createHotel(hotel);
        return ResponseEntity.ok(createdHotel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long id) {
        Hotel hotel = hotelService.getHotelById(id);
        List<RoomResponseDto> roomResponseDTOs = hotel.getRooms().stream()
                .map(room -> new RoomResponseDto(
                        room.getRoomNumber(),
                        room.getType(),
                        room.getPrice(),
                        room.isAvailable()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(HotelDto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .latitude(hotel.getLatitude())
                .longitude(hotel.getLongitude())
                .rooms(roomResponseDTOs)
                .build());
    }

    @GetMapping()
    public List<HotelDto> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        List<HotelDto> hotelResponseDTOs = new ArrayList<>();

        for (Hotel hotel : hotels) {
            List<RoomResponseDto> roomResponseDTOs = hotel.getRooms().stream()
                    .map(room -> new RoomResponseDto(
                            room.getRoomNumber(),
                            room.getType(),
                            room.getPrice(),
                            room.isAvailable()
                    ))
                    .collect(Collectors.toList());

            hotelResponseDTOs.add(new HotelDto(
                    hotel.getId(),
                    hotel.getName(),
                    hotel.getLatitude(),
                    hotel.getLongitude(),
                    roomResponseDTOs
            ));
        }

        return hotelResponseDTOs;
    }


    @PutMapping("/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable Long id, @RequestBody Hotel hotel) {
        Hotel updatedHotel = hotelService.updateHotel(id, hotel);
        return ResponseEntity.ok(updatedHotel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }
}

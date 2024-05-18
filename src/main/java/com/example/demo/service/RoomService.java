package com.example.demo.service;

import com.example.demo.dto.RoomCreateRequest;
import com.example.demo.entity.Hotel;
import com.example.demo.entity.Room;
import com.example.demo.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HotelService hotelService;

    public Room createRoom(RoomCreateRequest roomCreateRequest) {
        Long hotelId = roomCreateRequest.getHotelId();
        Hotel hotel = hotelService.getHotelById(hotelId);
        if (hotel == null) {
            return null;
        }
        System.out.println(roomCreateRequest);
        System.out.println(roomCreateRequest.isAvailable());
        Room room = new Room();
        room.setHotel(hotel);
        room.setRoomNumber(roomCreateRequest.getRoomNumber());
        room.setType(roomCreateRequest.getType());
        room.setPrice(roomCreateRequest.getPrice());
        room.setAvailable(roomCreateRequest.isAvailable());

        return roomRepository.save(room);
    }


    public Room getRoomById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room updateRoom(Long roomId, Room updatedRoom) {
        Room existingRoom = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        existingRoom.setHotel(updatedRoom.getHotel());
        existingRoom.setType(updatedRoom.getType());
        existingRoom.setPrice(updatedRoom.getPrice());
        existingRoom.setAvailable(updatedRoom.isAvailable());
        existingRoom.setReservations(updatedRoom.getReservations());

        return roomRepository.save(existingRoom);
    }

    public void deleteRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        roomRepository.delete(room);
    }
}

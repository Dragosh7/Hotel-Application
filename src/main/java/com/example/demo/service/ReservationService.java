package com.example.demo.service;

import com.example.demo.dto.ReservationRequest;
import com.example.demo.dto.ReservationResponse;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.User;
import com.example.demo.entity.Room;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public void updateRoomAvailability() {
        List<Room> rooms = roomRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (Room room : rooms) {
            room.getReservations().forEach(reservation -> {
                if (reservation.getDate().isBefore(now) && (reservation.getStatus() == null || !reservation.getStatus().equals("attended"))) {
                    reservation.setStatus("attended");
                    reservationRepository.save(reservation);
                }
            });

            boolean isAvailable = room.getReservations().stream()
                    .noneMatch(reservation -> reservation.getDate().isAfter(now) && (reservation.getStatus() == null || !reservation.getStatus().equals("attended")));
            room.setAvailable(isAvailable);
            roomRepository.save(room);
        }
    }
    public Reservation createReservation(ReservationRequest request) {
        User user= userService.getUserByUsername(request.getUsername());
        Room room = roomRepository.findByRoomNumberAndTypeAndPrice(request.getRoomNumber(), request.getType(), request.getPrice());
        room.setAvailable(false);
        Reservation reservation= Reservation.builder()
                .user(user)
                .room(room)
                .date(request.getDate())
                .status("reserved").build();
        return reservationRepository.save(reservation);
    }

    public Reservation getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));
    }

    public List<ReservationResponse> getReservationsOfUser(String username) {
        User user = userService.getUserByUsername(username);
        List<Reservation> reservations = reservationRepository.findAllByUser(user);
        List<ReservationResponse> reservationResponses = new ArrayList<>();
        for (Reservation reservation : reservations) {
            ReservationResponse reservationResponse = ReservationResponse.builder()
                    .id(reservation.getId())
                    .username(username)
                    .hotel(reservation.getRoom().getHotel().getName())
                    .roomNumber(reservation.getRoom().getRoomNumber())
                    .type(reservation.getRoom().getType())
                    .price(reservation.getRoom().getPrice())
                    .date(reservation.getDate())
                    .status(reservation.getStatus())
                    .build();
            reservationResponses.add(reservationResponse);
        }
        return reservationResponses;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation updateReservation(Long reservationId, Reservation updatedReservation) {
        Reservation existingReservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));

        if(existingReservation.canCancel()) {
            existingReservation.setRoom(updatedReservation.getRoom());
            existingReservation.setDate(updatedReservation.getDate());

            return reservationRepository.save(existingReservation);
        }
        else  throw new IllegalStateException("Too late to modify the reservation");
    }

    @Transactional
    public void deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));
        if (reservation.canCancel()) {
            Room room = reservation.getRoom();
            room.setAvailable(true);
            roomRepository.save(room);
            reservation.setStatus("canceled");
            reservationRepository.save(reservation);
        } else {
            throw new IllegalStateException("Cannot cancel reservation less than two hours");
        }

    }

}

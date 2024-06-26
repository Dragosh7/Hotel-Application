package com.example.demo.repository;

import com.example.demo.entity.Hotel;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByUserAndRoom_Hotel(User user, Hotel hotel);
    List<Reservation> findAllByUser(User user);
}

package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="room", uniqueConstraints = @UniqueConstraint(columnNames={"hotel_id", "room_number"}))
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    @JsonIgnore
    private Hotel hotel;
    @Column(name = "room_number")
    private Integer roomNumber;
    private Integer type; // 1: single, 2: double, 3: suite, 4: matrimonial
    private Double price;
    private boolean isAvailable;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();
    @Builder
    public Room(Long id, Hotel hotel, Integer roomNumber, Integer type, Double price, boolean isAvailable) {
        this.id = id;
        this.hotel = hotel;
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.isAvailable = isAvailable;
    }
    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setRoom(this);
    }

    public void removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
        reservation.setRoom(null);
    }
}

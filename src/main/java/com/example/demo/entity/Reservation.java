package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    private LocalDateTime date;
    private String status;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = "pending";
        }
    }
    public void cancel() {
        this.room.removeReservation(this);
        this.room = null;
    }

    public void changeRoom(Room newRoom) {
        this.room.removeReservation(this);
        this.room = newRoom;
        this.room.addReservation(this);
    }

    public boolean canCancel() {
        LocalDateTime twoHoursBeforeCheckIn = this.date.minusHours(2);
        return LocalDateTime.now().isBefore(twoHoursBeforeCheckIn);
    }

}

package com.example.demo.scheduleTasks;

import com.example.demo.service.ReservationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class Scheduler {

    private final ReservationService reservationService;

    public Scheduler(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Scheduled(fixedRate = 60000) // Runs every 60 seconds and checks whether reservations were completed
    public void updateRoomAvailability() {
        reservationService.updateRoomAvailability();
    }
}


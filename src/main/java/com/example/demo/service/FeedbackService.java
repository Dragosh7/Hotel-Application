package com.example.demo.service;

import com.example.demo.entity.Feedback;
import com.example.demo.entity.Hotel;
import com.example.demo.entity.Reservation;
import com.example.demo.repository.FeedbackRepository;
import com.example.demo.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public boolean hasReservationsForHotel(Reservation reservation) {
        Hotel hotel = reservation.getRoom().getHotel();
        return reservationRepository.findByUserAndRoom_Hotel(reservation.getUser(), hotel).isPresent();
    }
    public Feedback createFeedback(Long reservationId, int rating, String comment) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new EntityNotFoundException("Reservation not found"));

        if (hasReservationsForHotel(reservation)) {
            Feedback feedback = Feedback.builder()
                    .hotel(reservation.getRoom().getHotel())
                    .user(reservation.getUser())
                    .rating(rating)
                    .comment(comment)
                    .build();
            return feedbackRepository.save(feedback);
        } else {
            throw new IllegalStateException("Cannot leave feedback for reservation that has not been checked out");
        }
    }

    public Feedback getFeedbackById(Long feedbackId) {
        return feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new EntityNotFoundException("Feedback not found"));
    }

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    public Feedback updateFeedback(Long feedbackId, Feedback updatedFeedback) {
        Feedback existingFeedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new EntityNotFoundException("Feedback not found"));

        existingFeedback.setHotel(updatedFeedback.getHotel());
        existingFeedback.setUser(updatedFeedback.getUser());
        existingFeedback.setRating(updatedFeedback.getRating());
        existingFeedback.setComment(updatedFeedback.getComment());

        return feedbackRepository.save(existingFeedback);
    }

    public void deleteFeedback(Long feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new EntityNotFoundException("Feedback not found"));

        feedbackRepository.delete(feedback);
    }
}
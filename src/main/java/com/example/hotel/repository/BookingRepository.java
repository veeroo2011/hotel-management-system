package com.example.hotel.repository;

import com.example.hotel.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Returns true if there exists a booking for roomId that overlaps (checkIn, checkOut)
    boolean existsByRoomIdAndCheckOutAfterAndCheckInBefore(Long roomId, LocalDate checkIn, LocalDate checkOut);
}

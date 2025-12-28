package com.example.hotel.controller;

import com.example.hotel.model.Booking;
import com.example.hotel.model.Room;
import com.example.hotel.repository.BookingRepository;
import com.example.hotel.repository.RoomRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingRepository bookingRepo;
    private final RoomRepository roomRepo;

    public BookingController(BookingRepository bookingRepo, RoomRepository roomRepo) {
        this.bookingRepo = bookingRepo;
        this.roomRepo = roomRepo;
    }

    // Create new booking
    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {

    Long roomId = booking.getRoom().getId();

    // 1. Validate room exists
    Room room = roomRepo.findById(roomId)
            .orElseThrow(() -> new RuntimeException("Room not found with ID: " + roomId));

    // 2. Check if room already booked for given dates
    boolean isBooked = bookingRepo.existsByRoomIdAndCheckOutAfterAndCheckInBefore(
            roomId,
            booking.getCheckIn(),
            booking.getCheckOut()
    );

    if (isBooked) {
        throw new IllegalArgumentException(
                "Room is already booked for the selected dates"
        );
    }

    // 3. Save booking
    booking.setRoom(room);
    return bookingRepo.save(booking);
   }

    // List all bookings
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    // Fetch one booking
    @GetMapping("/{id}")
    public Booking getBooking(@PathVariable Long id) {
        return bookingRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
    }

    // Delete booking
    @DeleteMapping("/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingRepo.deleteById(id);
        return "Booking deleted with ID: " + id;
    }
}

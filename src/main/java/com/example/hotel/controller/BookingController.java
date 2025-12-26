package com.example.hotel.controller;

import com.example.hotel.model.Booking;
import com.example.hotel.model.Room;
import com.example.hotel.repository.BookingRepository;
import com.example.hotel.repository.RoomRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

        // Ensure room exists before booking
        Optional<Room> room = roomRepo.findById(booking.getRoom().getId());

        if (room.isEmpty()) {
            throw new RuntimeException("Room not found with ID: " + booking.getRoom().getId());
        }

        booking.setRoom(room.get());
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

package com.example.hotel.controller;

import com.example.hotel.model.Room;
import com.example.hotel.model.Hotel;
import com.example.hotel.repository.RoomRepository;
import com.example.hotel.repository.HotelRepository;
import com.example.hotel.repository.BookingRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDate;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomRepository roomRepo;
    private final HotelRepository hotelRepo;
    private final BookingRepository bookingRepo;

    public RoomController(RoomRepository roomRepo, HotelRepository hotelRepo, BookingRepository bookingRepo) {
        this.roomRepo = roomRepo;
        this.hotelRepo = hotelRepo;
        this.bookingRepo = bookingRepo;
    }

    // Create room under a hotel
    @PostMapping
    public Room createRoom(@RequestBody Room room) {

        Long hotelId = room.getHotel().getId();

        Optional<Hotel> hotel = hotelRepo.findById(hotelId);
        if (hotel.isEmpty()) {
            throw new RuntimeException("Hotel not found with ID: " + hotelId);
        }

        room.setHotel(hotel.get());
        return roomRepo.save(room);
    }

    // List all rooms
    @GetMapping
    public List<Room> getAllRooms() {
        return roomRepo.findAll();
    }

    // Get single room
    @GetMapping("/{id}")
    public Room getRoom(@PathVariable Long id) {
        return roomRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with ID: " + id));
    }

    // Get all rooms for a specific hotel
    @GetMapping("/hotel/{hotelId}")
    public List<Room> getRoomsByHotel(@PathVariable Long hotelId) {
        Optional<Hotel> hotel = hotelRepo.findById(hotelId);

        if (hotel.isEmpty()) {
            throw new RuntimeException("Hotel not found with ID: " + hotelId);
        }

        return roomRepo.findByHotelId(hotelId);
    }

    // Get count of rooms for a specific hotel
    @GetMapping("/hotel/{hotelId}/count")
    public Long getRoomCountByHotel(@PathVariable Long hotelId) {
        Optional<Hotel> hotel = hotelRepo.findById(hotelId);

        if (hotel.isEmpty()) {
            throw new RuntimeException("Hotel not found with ID: " + hotelId);
        }

        return roomRepo.countByHotelId(hotelId);
    }

    // Check availability for a hotel within a date range
    // Example: GET /rooms/hotel/1/available?checkIn=2025-12-01&checkOut=2025-12-05
    @GetMapping("/hotel/{hotelId}/available")
    public List<Room> getAvailableRoomsForHotel(
            @PathVariable Long hotelId,
            @RequestParam("checkIn") String checkInStr,
            @RequestParam("checkOut") String checkOutStr) {

        Optional<Hotel> hotel = hotelRepo.findById(hotelId);
        if (hotel.isEmpty()) {
            throw new RuntimeException("Hotel not found with ID: " + hotelId);
        }

        LocalDate checkIn = LocalDate.parse(checkInStr);
        LocalDate checkOut = LocalDate.parse(checkOutStr);

        List<Room> rooms = roomRepo.findByHotelId(hotelId);

        // A room is available if there is NO booking that overlaps the requested period.
        return rooms.stream()
                .filter(r -> !bookingRepo.existsByRoomIdAndCheckOutAfterAndCheckInBefore(r.getId(), checkIn, checkOut))
                .collect(Collectors.toList());
    }
}

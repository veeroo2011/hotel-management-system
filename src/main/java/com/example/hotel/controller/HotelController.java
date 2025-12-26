package com.example.hotel.controller;
import com.example.hotel.model.Hotel;
import com.example.hotel.repository.HotelRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    private final HotelRepository repo;
    public HotelController(HotelRepository repo){ this.repo = repo; }

    @PostMapping
    public Hotel create(@RequestBody Hotel h){ return repo.save(h); }

    @GetMapping
    public List<Hotel> list(){ return repo.findAll(); }
}

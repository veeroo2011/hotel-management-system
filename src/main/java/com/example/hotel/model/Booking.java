package com.example.hotel.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Booking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private LocalDate checkIn;
    private LocalDate checkOut;

    @ManyToOne
    private Room room;

    // getters/setters...
    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }
    public String getCustomerName(){ return customerName; }
    public void setCustomerName(String c){ this.customerName = c; }
    public LocalDate getCheckIn(){ return checkIn; }
    public void setCheckIn(LocalDate d){ this.checkIn = d; }
    public LocalDate getCheckOut(){ return checkOut; }
    public void setCheckOut(LocalDate d){ this.checkOut = d; }
    public Room getRoom(){ return room; }
    public void setRoom(Room r){ this.room = r; }
}

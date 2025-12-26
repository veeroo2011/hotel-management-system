package com.example.hotel.model;

import jakarta.persistence.*;

@Entity
public class Room {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomNumber;
    private String type;
    private double price;

    @ManyToOne
    private Hotel hotel;

    // getters/setters...
    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }
    public String getRoomNumber(){ return roomNumber; }
    public void setRoomNumber(String rn){ this.roomNumber = rn; }
    public String getType(){ return type; }
    public void setType(String type){ this.type = type; }
    public double getPrice(){ return price; }
    public void setPrice(double price){ this.price = price; }
    public Hotel getHotel(){ return hotel; }
    public void setHotel(Hotel hotel){ this.hotel = hotel; }
}

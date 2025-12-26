package com.example.hotel.model;

import jakarta.persistence.*;

@Entity
public class Hotel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String city;
    private double rating;

    // getters/setters
    // (generate via IDE or write manually)
    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }
    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }
    public String getCity(){ return city; }
    public void setCity(String city){ this.city = city; }
    public double getRating(){ return rating; }
    public void setRating(double rating){ this.rating = rating; }
}

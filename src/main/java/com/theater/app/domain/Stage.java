package com.theater.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int capacity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stage")
    private List<Seat> seats;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stage")
    private List<Repertoire> repertoires = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public List<Repertoire> getRepertoires() {
        return repertoires;
    }

    public void setRepertoires(List<Repertoire> repertoires) {
        this.repertoires = repertoires;
    }
}

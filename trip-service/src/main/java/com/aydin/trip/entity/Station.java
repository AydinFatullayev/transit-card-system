package com.aydin.trip.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "stations")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private Integer sequenceNumber;

    public Station() {
    }

    public Station(Long id, String name, Integer sequenceNumber) {
        this.id = id;
        this.name = name;
        this.sequenceNumber = sequenceNumber;
    }

}
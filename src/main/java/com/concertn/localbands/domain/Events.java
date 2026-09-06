package com.concertn.localbands.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Events {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id",nullable = false,updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="bands_id")
    private Bands band;

    @Column(name="likes",nullable = false)
    private Integer likes = 0;

    //Location:
    @Column(name="latitude")
    private double latitude;

    @Column(name="longitude")
    private double longitude;

    //TODO: data for storing promotion pictures?

    @Column(name="date", nullable = false)
    private LocalDateTime date;

    @Column(name = "doors_open")
    private LocalDateTime doorsOpen;

    //don't need doors closed (unnecessary for now)
}

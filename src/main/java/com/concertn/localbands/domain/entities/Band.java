package com.concertn.localbands.domain.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="bands")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Band {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id",nullable = false,updatable = false)
    private UUID id;

    @Column(name="band_name",nullable = false)
    private String band_name;

    @Column(name="normalized_name",nullable = false)
    private String normalizedName;

    //TODO: how do we add images for s3?

    @ManyToMany
    @JoinTable(
            name = "band_events",
            joinColumns = @JoinColumn(name = "band_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    @Builder.Default
    private List<Event> events = new ArrayList<>();

}

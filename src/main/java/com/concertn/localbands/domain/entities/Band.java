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
public class Band {

    @Id
    @Column(name="id",nullable = false,updatable = false)
    private UUID id;

    @Column(name="band_name",nullable = false)
    private String band_name;

    @Column(name="normalized_name",nullable = false)
    private String normalized_name;

    //TODO: how do we add images for s3?

    @OneToMany(mappedBy = "band", cascade = CascadeType.ALL)
    private List<Event> events = new ArrayList<>();

}

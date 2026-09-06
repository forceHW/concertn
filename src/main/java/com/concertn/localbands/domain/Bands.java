package com.concertn.localbands.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="bands")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bands {

    @Id
    @Column(name="id",nullable = false,updatable = false)
    private UUID id;

    @Column(name="band_name",nullable = false)
    private String band_name;

    //TODO: how do we add images for s3?

    @OneToMany(mappedBy = "bands", cascade = CascadeType.ALL)
    private List<Events> events = new ArrayList<>();

}

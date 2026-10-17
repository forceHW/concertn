package com.concertn.localbands.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class getEventsByLocation {


    private double latitude;
    private double longitude;
    private double radiusMeters;
}

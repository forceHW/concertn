package com.concertn.localbands.services;

import com.concertn.localbands.domain.entities.Event;
import org.springframework.data.domain.Page;

public interface EventsService {


    Page<Event> fetchEventsByLocation(double latitude, double longitude, double radiusMeters);
}

package com.concertn.localbands.services;

import com.concertn.localbands.domain.entities.Event;
import com.concertn.localbands.domain.getEventsByLocation;
import org.springframework.data.domain.Page;

public interface EventsService {


    Page<Event> fetchEventsByLocation(getEventsByLocation eventRequestDetails);
}

package com.concertn.localbands.controllers;


import com.concertn.localbands.domain.entities.Event;
import com.concertn.localbands.domain.getEventsByLocation;
import com.concertn.localbands.domain.getEventsByLocationRequest;
import com.concertn.localbands.domain.getEventsByLocationResponse;
import com.concertn.localbands.mappers.EventMapper;
import com.concertn.localbands.services.EventsService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventMapper eventMapper;
    private final EventsService eventsService;

    @PostMapping
    public ResponseEntity<Page<getEventsByLocationResponse>> getEventsByLocation(
        @Valid @RequestBody getEventsByLocationRequest getEventsByLocationRequest
    ){


        getEventsByLocation getEventsDetails = eventMapper.fromDto(getEventsByLocationRequest);

        Page<Event> events = eventsService.fetchEventsByLocation(getEventsDetails);


        return ResponseEntity.ok(events.map(eventMapper::toDto));
    }
}

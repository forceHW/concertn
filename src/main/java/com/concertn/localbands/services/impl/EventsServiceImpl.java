package com.concertn.localbands.services.impl;

import com.concertn.localbands.domain.dtos.AIEventResponseDto;
import com.concertn.localbands.domain.dtos.NearbySearchResponse;
import com.concertn.localbands.domain.entities.Event;
import com.concertn.localbands.services.AiParseService;
import com.concertn.localbands.services.EventsService;
import com.concertn.localbands.services.NearbyPlacesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Arrays.stream;


@Service
@RequiredArgsConstructor
public class EventsServiceImpl implements EventsService {

    private final AiParseService aiParseService;
    private final NearbyPlacesService nearbyPlacesService;

    @Override
    public Page<Event> fetchEventsByLocation(double latitude, double longitude, double radiusMeters) {
        List<NearbySearchResponse.Place> places = nearbyPlacesService.findNearbyVenues(latitude,longitude,radiusMeters);

//      scrape through every given website - return all events
        List<List<AIEventResponseDto>> events = places.stream().map(
                aiParseService::ParseByPlace
        ).toList();

        //save it?


        return null;
    }
}

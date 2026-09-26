package com.concertn.localbands.services.impl;

import com.concertn.localbands.domain.dtos.AIEventResponseDto;
import com.concertn.localbands.domain.dtos.NearbySearchResponse;
import com.concertn.localbands.domain.entities.Band;
import com.concertn.localbands.domain.entities.Event;
import com.concertn.localbands.repositories.BandRepository;
import com.concertn.localbands.repositories.EventRepository;
import com.concertn.localbands.services.AiParseService;
import com.concertn.localbands.services.EventsService;
import com.concertn.localbands.services.NearbyPlacesService;
import com.concertn.localbands.tools.EventAiTools;
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
    private final EventRepository eventRepository;
    private final BandRepository bandRepository;

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

    private void saveEvent(AIEventResponseDto aiEventResponseDto){
        Event event = Event.builder()
                .event_name(aiEventResponseDto.getEventName())
                .date(aiEventResponseDto.getDate())
                .doorsOpen(aiEventResponseDto.getDoorsOpen())

                                .build();


      /**TODO: do check for band if it exists or nah, if no create a new one and save it into band repo
       *
       * , finish this function, double check id gen
        how can i set lat and long,
       create a priv function for normalized band name

        */

//                .band(aiEventResponseDto.getBandName())
//                .date(aiEventResponseDto.getDate())
//                .doorsOpen();
    }
}

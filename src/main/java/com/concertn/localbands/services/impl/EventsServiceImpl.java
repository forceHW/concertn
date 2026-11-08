package com.concertn.localbands.services.impl;

import com.concertn.localbands.domain.dtos.AIEventResponseDto;
import com.concertn.localbands.domain.dtos.AIEventResponseWithLatLongDto;
import com.concertn.localbands.domain.dtos.NearbySearchResponse;
import com.concertn.localbands.domain.entities.Band;
import com.concertn.localbands.domain.entities.Event;
import com.concertn.localbands.domain.getEventsByLocation;
import com.concertn.localbands.repositories.BandRepository;
import com.concertn.localbands.repositories.EventRepository;
import com.concertn.localbands.services.AiParseService;
import com.concertn.localbands.services.EventsService;
import com.concertn.localbands.services.NearbyPlacesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
@Slf4j
public class EventsServiceImpl implements EventsService {

    private final AiParseService aiParseService;
    private final NearbyPlacesService nearbyPlacesService;
    private final EventRepository eventRepository;
    private final BandRepository bandRepository;

    @Override
    @Transactional
    public Page<Event> fetchEventsByLocation(getEventsByLocation getEventsDetails) {
        double latitude = getEventsDetails.getLatitude();
        double longitude = getEventsDetails.getLongitude();
        double radiusMeters = getEventsDetails.getRadiusMeters();


        List<NearbySearchResponse.Place> places = nearbyPlacesService.findNearbyVenues(latitude, longitude, radiusMeters);


        places = places.stream().filter(this::hasUsableData).toList();

//        log.debug("places after filter: {}", places);

//      scrape through every given website - return all events
        List<Event> events = places.stream().flatMap(place -> {
            List<AIEventResponseDto> parsedEventofPlace = aiParseService.ParseByPlace(place);
//
            if (parsedEventofPlace == null || parsedEventofPlace.isEmpty()) {
                log.info("AI returned null/empty dto for the place {}", place.displayName().text());
                return Stream.empty();
            }
//
//
            List<Event> toEventsofPlace = parsedEventofPlace.stream().filter(eventdto ->
                    eventdto != null && eventdto.getEventName() != null && !eventdto.getEventName().isBlank()
            ).map(eventdto -> {
                List<Band> bandsToAdd;
                if (eventdto.getBandName() != null && !eventdto.getBandName().isEmpty()) {
                    bandsToAdd = eventdto.getBandName().stream().map(newBand -> {
                        Optional<Band> getBand = bandRepository.findByNormalizedName(normalizeText(newBand));
                        return getBand.orElseGet(() -> createBand(newBand, eventdto));
                    }).toList();
                } else {
                    bandsToAdd = new ArrayList<>();
                }

                return createEvent(eventdto, bandsToAdd, place.formattedAddress(), place.location().latitude(), place.location().longitude());
            }).toList();
//
//
            return toEventsofPlace.stream();
        }).toList();
        return new PageImpl<>(events);
    }


//        List<Event> allEvents = events.stream().flatMap(
//                eventsOfPlace -> {
//                    //what do i want to do with all the events in one place?
//
//                    //TODO: exception handling for when a dto is null and or list<dto> is null?
//                    if (eventsOfPlace != null && !eventsOfPlace.isEmpty()) return eventsOfPlace.stream().filter(eventCheckArgs ->
//                            eventCheckArgs != null && eventCheckArgs.getEventName() != null && !eventCheckArgs.getEventName().isBlank()
//                            ).map(eventToAdd -> {
//                        //what do i want to do with a event
//                        //lookup/create for all the given bands (each dto has a list of artists
//
//
//                        List<Band> bandsToAdd;
//                        if ( eventToAdd.getBandName() != null && !eventToAdd.getBandName().isEmpty()) {  //if there is specified bands, lets add them to our db, otherwise just pass it in events as null
//                                bandsToAdd = eventToAdd.getBandName().stream().map(newBand -> {   // This creates all the bands in a event dto
//                                        Optional<Band> getBand = bandRepository.findByNormalizedName(normalizeText(newBand));
//                                        return getBand.orElseGet(() -> createBand(newBand, eventToAdd));
//                                    }
//
//                            ).toList();
//                        }
//                        else {
//                            bandsToAdd = new ArrayList<>();
//                        }
//                        //now create the event
//                        return createEvent(eventToAdd,bandsToAdd,latitude,longitude);
//                            }
//
//
//                    );
//                    else{
//                        //this else will occur when a list<eventdto> is null -> which means that the ai returned null/nothing for this specific venue/place
//                        log.warn("AI returned no events for a venue, skipping");
//                        return Stream.empty();
//                    }
//                }
//
//
//        ).toList();
////        return null;
//      return new PageImpl<>(allEvents);
//    }

    private Event createEvent(AIEventResponseDto aiEventResponseDto, List<Band> bands, String formattedAddress, double latitude, double longitude){
        Event event = Event.builder()
                .event_name(aiEventResponseDto.getEventName())
                .bands(bands)
                .latitude(latitude)
                .longitude(longitude)
                .formattedAddress(formattedAddress)
//                TODO: Image S3?
                .date(aiEventResponseDto.getDate())
                .doorsOpen(aiEventResponseDto.getDoorsOpen())


                .build();

        return eventRepository.save(event);
    }

    private Band createBand(String bandName, AIEventResponseDto aiEventResponseDto){
        Band band = Band.builder()
                .band_name(bandName)
                .normalizedName(normalizeText(bandName))
                .build();

        return bandRepository.save(band);
    }


    /**
     * Removes all white text and makes all the letters lowercase
     * We use this to store band names for lookups
     * By using the normalized text, we are able to minimize errors when looking up and saving events of the same band
     * @param text input text
     * @return normalized text
     */
    private String normalizeText(String text){
        return text.toLowerCase().replaceAll("\\s+", "");
    }

    /**
     * used to filter responses from Google places api, places with no name or website uri shouldnt be passed into our ai parser
     * we check to see if there is a valid name and website
     * @param place
     * @return
     */
    private boolean hasUsableData(NearbySearchResponse.Place place) {
        return place.displayName() != null
                && place.displayName().text() != null
                && !place.displayName().text().isBlank()
                && place.websiteUri() != null
                && !place.websiteUri().isBlank();
    }
}

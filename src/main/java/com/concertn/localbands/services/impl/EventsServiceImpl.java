package com.concertn.localbands.services.impl;

import com.concertn.localbands.domain.dtos.AIEventResponseDto;
import com.concertn.localbands.domain.dtos.NearbySearchResponse;
import com.concertn.localbands.domain.entities.Band;
import com.concertn.localbands.domain.entities.Event;
import com.concertn.localbands.exceptions.VenueEventsException;
import com.concertn.localbands.repositories.BandRepository;
import com.concertn.localbands.repositories.EventRepository;
import com.concertn.localbands.services.AiParseService;
import com.concertn.localbands.services.EventsService;
import com.concertn.localbands.services.NearbyPlacesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EventsServiceImpl implements EventsService {

    private final AiParseService aiParseService;
    private final NearbyPlacesService nearbyPlacesService;
    private final EventRepository eventRepository;
    private final BandRepository bandRepository;

    @Override
    @Transactional
    public Page<Event> fetchEventsByLocation(double latitude, double longitude, double radiusMeters) {
        List<NearbySearchResponse.Place> places = nearbyPlacesService.findNearbyVenues(latitude,longitude,radiusMeters);

//      scrape through every given website - return all events
        List<List<AIEventResponseDto>> events = places.stream().map(
                aiParseService::ParseByPlace
        ).toList();


        //TODO: exception handling here: when the returned nested var "events" is null

        List<Event> allEvents = events.stream().flatMap(
                eventsOfPlace -> {
                    //what do i want to do with all the events in one place?

                    //TODO: exception handling for when a dto is null and or list<dto> is null?
                    if (eventsOfPlace != null) return eventsOfPlace.stream().map(eventToAdd -> {
                        //what do i want to do with a event
                        //lookup/create for all the given bands (each dto has a list of artists)
                        List<Band> bandsToAdd = eventToAdd.getBandName().stream().map(newBand -> {   // This creates all the bands in a event dto
                                    Optional<Band> getBand = bandRepository.findByNormalizedName(normalizeText(newBand));
                                    return Optional.of(getBand).orElseThrow().orElse(createBand(newBand, eventToAdd));
                                }

                        ).toList();
                        //now create the event
                        return createEvent(eventToAdd,bandsToAdd,latitude,longitude);
                            }


                    );
                    else{
                        //this else will occur when a list<eventdto> is null -> which means that the ai returned null/nothing for this specific venue/place
                        //TODO: consider redundancy as AI also has the option to throw an exception when they cant return a list of eventsDTO
                        throw new VenueEventsException("Ai returned null on a placedto");
                    }
                }


        ).toList();

        return new PageImpl<>(allEvents);
    }

    private Event createEvent(AIEventResponseDto aiEventResponseDto, List<Band> bands, double latitude, double longitude){
        Event event = Event.builder()
                .event_name(aiEventResponseDto.getEventName())
                .bands(bands)
                .latitude(latitude)
                .longitude(longitude)
//                TODO: Formated Address?
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
}

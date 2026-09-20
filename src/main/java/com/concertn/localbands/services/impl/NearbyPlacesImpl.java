package com.concertn.localbands.services.impl;

import com.concertn.localbands.services.NearbyPlacesService;
import com.concertn.localbands.domain.dtos.NearbySearchRequest;
import com.concertn.localbands.domain.dtos.NearbySearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NearbyPlacesImpl implements NearbyPlacesService {

    private static final String SEARCH_NEARBY_PATH = "/v1/places:searchNearby";

    // Only ask for what we actually use - Places API bills per requested field.
    private static final String FIELD_MASK = String.join(",",
            "places.id",
            "places.displayName",
            "places.formattedAddress",
            "places.location",
            "places.types",
            "places.liveMusic",
            "places.websiteUri"
    );

    // Adjust to the venue types you actually care about surfacing.
    private static final List<String> VENUE_TYPES = List.of("concert_hall");


    private static final int MAX_RESULTS = 20;

    private final RestClient googlePlacesClient;

    @Override
    public List<NearbySearchResponse.Place> findNearbyVenues(double latitude, double longitude, double radiusMeters) {
        NearbySearchRequest request = new NearbySearchRequest(
                VENUE_TYPES,
                MAX_RESULTS,
                new NearbySearchRequest. LocationRestriction(
                        new NearbySearchRequest.Circle(
                                new NearbySearchRequest.Center(latitude, longitude),
                                radiusMeters
                        )
                )
        );

        NearbySearchResponse response = googlePlacesClient.post()
                .uri(SEARCH_NEARBY_PATH)
                .header("X-Goog-FieldMask", FIELD_MASK)
                .body(request)
                .retrieve()
                .body(NearbySearchResponse.class);

        return response != null && response.places() != null ? response.places() : List.of();
    }
}

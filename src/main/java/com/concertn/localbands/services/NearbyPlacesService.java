package com.concertn.localbands.services;

import com.concertn.localbands.services.dto.google.NearbySearchResponse;

import java.util.List;

public interface NearbyPlacesService {

    /**
     * Finds venues near a given point using Google Places Nearby Search.
     *
     * @param latitude     center point latitude
     * @param longitude    center point longitude
     * @param radiusMeters search radius in meters (Google caps this at 50000)
     */
    List<NearbySearchResponse.Place> findNearbyVenues(double latitude, double longitude, double radiusMeters);
}

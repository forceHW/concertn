package com.concertn.localbands.services.dto.google;

import java.util.List;

/**
 * Response body from POST https://places.googleapis.com/v1/places:searchNearby.
 * Only fields covered by our X-Goog-FieldMask will be populated - Places API
 * bills by requested field group, so keep the mask (and this record) minimal.
 */
public record NearbySearchResponse(List<Place> places) {

    public record Place(
            String id,
            DisplayName displayName,
            String formattedAddress,
            Location location,
            List<String> types
    ) {}

    public record DisplayName(String text, String languageCode) {}

    public record Location(double latitude, double longitude) {}
}

package com.concertn.localbands.domain.dtos;

import java.util.List;

/**
 * Request body for POST https://places.googleapis.com/v1/places:searchNearby
 * Shape follows Google's Places API (New) contract exactly - keep this record
 * dumb and vendor-specific, don't let it leak into the domain model.
 */
public record NearbySearchRequest(
        List<String> includedTypes,
        int maxResultCount,
        LocationRestriction locationRestriction
) {
    public record LocationRestriction(Circle circle) {}

    public record Circle(Center center, double radius) {}

    public record Center(double latitude, double longitude) {}
}

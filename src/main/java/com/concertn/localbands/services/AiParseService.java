package com.concertn.localbands.services;

import com.concertn.localbands.domain.dtos.NearbySearchResponse;

public interface AiParseService {

    Boolean ParsePlaces(NearbySearchResponse nearbySearchResponse);
}

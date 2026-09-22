package com.concertn.localbands.services;

import com.concertn.localbands.domain.dtos.AIEventResponseDto;
import com.concertn.localbands.domain.dtos.NearbySearchResponse;

public interface AiParseService {

    public AIEventResponseDto ParsePlaces(NearbySearchResponse.Place place);
}

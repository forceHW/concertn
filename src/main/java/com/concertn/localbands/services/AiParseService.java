package com.concertn.localbands.services;

import com.concertn.localbands.domain.dtos.AIEventResponseDto;
import com.concertn.localbands.domain.dtos.NearbySearchResponse;

import java.util.List;

public interface AiParseService {

    public List<AIEventResponseDto> ParsePlaces(NearbySearchResponse.Place place);
}

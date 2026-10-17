package com.concertn.localbands.mappers;

import com.concertn.localbands.domain.dtos.getBandsByLocationResponseDto;
import com.concertn.localbands.domain.entities.Band;
import com.concertn.localbands.domain.entities.Event;
import com.concertn.localbands.domain.getEventsByLocation;
import com.concertn.localbands.domain.getEventsByLocationRequest;
import com.concertn.localbands.domain.getEventsByLocationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    getEventsByLocation fromDto(getEventsByLocationRequest getEventsByLocationRequest);
    getEventsByLocationResponse toDto(Event event);

    getBandsByLocationResponseDto tDto(Band band);

}

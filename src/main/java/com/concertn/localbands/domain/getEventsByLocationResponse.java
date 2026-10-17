package com.concertn.localbands.domain;


import com.concertn.localbands.domain.dtos.getBandsByLocationResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class getEventsByLocationResponse {

    private String event_name;
    private List<getBandsByLocationResponseDto> bands;
    private Integer likes;
    private String formattedAddress;
    private LocalDateTime date;
    private LocalDateTime doorsOpen;
}

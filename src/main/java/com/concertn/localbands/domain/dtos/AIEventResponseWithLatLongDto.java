package com.concertn.localbands.domain.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class AIEventResponseWithLatLongDto {
    private String eventName;
    private List<String> bandName;
    private String formatedString;
    private String latitude;
    private String longitude;
    private LocalDateTime date;
    private LocalDateTime doorsOpen;
}

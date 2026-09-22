package com.concertn.localbands.domain.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class AIEventResponseDto {
    private String eventName;
    private List<String> bandName;
    private String formatedString;
    private LocalDateTime date;
    private LocalDateTime doorsOpen;
}

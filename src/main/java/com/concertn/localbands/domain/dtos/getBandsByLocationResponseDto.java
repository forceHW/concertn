package com.concertn.localbands.domain.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class getBandsByLocationResponseDto {
    private String band_name;
}

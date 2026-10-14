package com.concertn.localbands.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class PageDataDto {

    private String pageContent;
    private List<String> paths;


}

package com.concertn.localbands.controllers;


import com.concertn.localbands.domain.dtos.ErrorDto;
import com.concertn.localbands.exceptions.AiError;
import com.concertn.localbands.exceptions.PlacesNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(PlacesNotFoundException.class)
    public ResponseEntity<ErrorDto> handlePlacesNotFoundException(PlacesNotFoundException ex){
        log.error("Caught Places Not Found Exception", ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("Places Not Found");
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(AiError.class)
    public ResponseEntity<ErrorDto> handleAiError(AiError ex){
        log.error("Ai has thrown error at parse service level");
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("Failed to parse venues");
        return new ResponseEntity<>(errorDto,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

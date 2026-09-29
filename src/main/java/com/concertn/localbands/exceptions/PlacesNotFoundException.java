package com.concertn.localbands.exceptions;

public class PlacesNotFoundException extends ConcertException{

    public PlacesNotFoundException(){

    }

    public PlacesNotFoundException(String message){
        super(message);
    }

    public PlacesNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

    public PlacesNotFoundException(Throwable cause){
        super(cause);
    }

    public PlacesNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

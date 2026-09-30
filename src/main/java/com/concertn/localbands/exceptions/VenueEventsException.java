package com.concertn.localbands.exceptions;

public class VenueEventsException extends ConcertException{

    public VenueEventsException(){

    }

    public VenueEventsException(String message){
        super(message);
    }

    public VenueEventsException(String message, Throwable cause){
        super(message, cause);
    }

    public VenueEventsException(Throwable cause){
        super(cause);
    }

    public VenueEventsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

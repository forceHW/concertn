package com.concertn.localbands.exceptions;

//general exception class for all exceptions that I will be throwing
public class ConcertException extends RuntimeException{

    public ConcertException(){

    }

    public ConcertException(String message){
        super(message);
    }

    public ConcertException(String message, Throwable cause){
        super(message, cause);
    }

    public ConcertException(Throwable cause){
        super(cause);
    }

    public ConcertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package com.concertn.localbands.exceptions;


public class AiError extends ConcertException{

    public AiError(){

    }

    public AiError(String message){
        super(message);
    }

    public AiError(String message, Throwable cause){
        super(message, cause);
    }

    public AiError(Throwable cause){
        super(cause);
    }

    public AiError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

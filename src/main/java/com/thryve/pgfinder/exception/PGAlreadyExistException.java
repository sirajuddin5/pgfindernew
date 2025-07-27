package com.thryve.pgfinder.exception;

public class PGAlreadyExistException extends RuntimeException{
    public PGAlreadyExistException(String Message){
        super(Message);
    }
}

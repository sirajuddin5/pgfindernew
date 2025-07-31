package com.thryve.pgfinder.exception;

public class AlreadyExistException extends RuntimeException{
    public AlreadyExistException(String Message){
        super(Message);
    }
}

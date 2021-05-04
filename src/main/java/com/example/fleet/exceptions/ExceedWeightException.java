package com.example.fleet.exceptions;

public class ExceedWeightException extends RuntimeException {

    public ExceedWeightException(String errorMessage) {
        super(errorMessage);
    }
}

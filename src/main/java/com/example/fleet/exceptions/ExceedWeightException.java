package com.example.fleet.exceptions;

public class ExceedWeightException extends Exception {

    public ExceedWeightException(String errorMessage) {
        super(errorMessage);
    }
}

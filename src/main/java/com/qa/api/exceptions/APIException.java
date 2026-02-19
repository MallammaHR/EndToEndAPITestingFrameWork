package com.qa.api.exceptions;

public class APIException extends RuntimeException {

    public APIException(String message) {
        super(message);
    }
}
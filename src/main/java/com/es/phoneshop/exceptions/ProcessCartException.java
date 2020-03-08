package com.es.phoneshop.exceptions;

public class ProcessCartException extends RuntimeException {
    private int statusCode;

    public ProcessCartException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

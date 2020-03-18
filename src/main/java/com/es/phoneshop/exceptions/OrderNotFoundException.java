package com.es.phoneshop.exceptions;

public class OrderNotFoundException extends RuntimeException {
    private String id;

    public OrderNotFoundException() {
        super();
    }

    public OrderNotFoundException(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

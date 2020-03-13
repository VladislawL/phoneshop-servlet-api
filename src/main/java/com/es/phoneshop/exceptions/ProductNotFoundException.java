package com.es.phoneshop.exceptions;

public class ProductNotFoundException extends RuntimeException {
    private Long id;

    public ProductNotFoundException(Long id) {
        this.id = id;
    }
}

package com.es.phoneshop.exceptions;

public class NegativeQuantityException extends RuntimeException {
    private long quantity;

    public NegativeQuantityException(long quantity) {
        this.quantity = quantity;
    }

    public long getQuantity() {
        return quantity;
    }
}

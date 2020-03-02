package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

public class OutOfStockException extends RuntimeException {
    private Product product;
    private long quantityRequested;

    public OutOfStockException(Product product, long quantityRequested) {
        this.product = product;
        this.quantityRequested = quantityRequested;
    }

    public Product getProduct() {
        return product;
    }
}

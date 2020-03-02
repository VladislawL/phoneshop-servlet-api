package com.es.phoneshop.model.cart;

public class CartItem {
    private long productId;
    private long quantity;

    public CartItem() {
    }

    public CartItem(long productId, long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return productId + "=" + quantity;
    }
}

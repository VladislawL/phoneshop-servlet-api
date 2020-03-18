package com.es.phoneshop.services.cartsevice;

import com.es.phoneshop.model.cart.Cart;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    Cart getCart(HttpServletRequest request);
    void add(Cart cart, long productId, long quantity);
    void update(Cart cart, long productId, long quantity);
    void delete(Cart cart, long productId);
    void deleteCart(HttpServletRequest request);
}

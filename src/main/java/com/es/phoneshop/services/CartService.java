package com.es.phoneshop.services;

import com.es.phoneshop.model.cart.Cart;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public interface CartService {
    Cart getCart(HttpServletRequest request);
    void add(Cart cart, long productId, long quantity);
    void update(Cart cart, long productId, long quantity);
    void delete(Cart cart, long productId);
    String formatTotalPrice(Cart cart, Locale locale);
}

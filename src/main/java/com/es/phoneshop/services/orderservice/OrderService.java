package com.es.phoneshop.services.orderservice;

import com.es.phoneshop.exceptions.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;

public interface OrderService {
    void placeOrder(Order order);
    Order getOrder(String secureId) throws OrderNotFoundException;
}

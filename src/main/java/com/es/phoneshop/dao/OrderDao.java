package com.es.phoneshop.dao;

import com.es.phoneshop.model.order.Order;

import java.util.Optional;

public interface OrderDao extends Dao<Order> {
    void save(Order order);
    <String> Optional<Order> getItem(String secureId);
}

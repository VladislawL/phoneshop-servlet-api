package com.es.phoneshop.dao;

import com.es.phoneshop.model.order.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArrayListOrderDao implements OrderDao {
    private List<Order> orders;
    private static ArrayListOrderDao instance;

    public synchronized static ArrayListOrderDao getInstance() {
        if (instance == null) {
            instance = new ArrayListOrderDao();
        }
        return instance;
    }

    private ArrayListOrderDao() {
        orders = new ArrayList<>();
    }

    @Override
    public void save(Order order) {
        orders.add(order);
    }

    @Override
    public <String> Optional<Order> getItem(String secureId) {
        return orders.stream()
                .filter(order -> order.getSecureId().equals(secureId))
                .findFirst();
    }
}

package com.es.phoneshop.services.orderservice;

import com.es.phoneshop.dao.ArrayListOrderDao;
import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.exceptions.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.PaymentMethod;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DefaultOrderService implements OrderService {
    private static DefaultOrderService instance;
    private OrderDao orderDao;

    private DefaultOrderService() {
        orderDao = ArrayListOrderDao.getInstance();
    }

    public synchronized static DefaultOrderService getInstance() {
        if (instance == null) {
            instance = new DefaultOrderService();
        }
        return instance;
    }

    @Override
    public void placeOrder(Order order) {
        UUID uuid = UUID.randomUUID();

        order.setSecureId(uuid.toString());

        orderDao.save(order);
    }

    @Override
    public Order getOrder(String secureId) throws OrderNotFoundException {
        return orderDao.getItem(secureId).orElseThrow(() -> new OrderNotFoundException(secureId));
    }

    protected void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public static Map<String, PaymentMethod> getPaymentMethodMap() {
        Map<String, PaymentMethod> result = new HashMap<>();
        EnumSet<PaymentMethod> paymentMethods = EnumSet.allOf(PaymentMethod.class);

        for (PaymentMethod paymentMethod: paymentMethods) {
            result.put(paymentMethod.name(), paymentMethod);
        }

        return  result;
    }
}

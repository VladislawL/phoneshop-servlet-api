package com.es.phoneshop.model.cart;

import com.es.phoneshop.dao.ArrayListOrderDao;
import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.model.client.Client;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.PaymentMethod;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ArrayListCartDaoTest {
    private OrderDao orderDao;
    private String testId = "0";

    @Before
    public void setup() {
        orderDao = ArrayListOrderDao.getInstance();
        orderDao.save(generateEmptyOrder(testId));
    }

    @Test
    public void shouldSaveOneOrder() {
        String id = "1";
        Order expectedOrder = generateEmptyOrder(id);

        orderDao.save(expectedOrder);
        Order order = orderDao.getItem(id).orElse(null);

        assertEquals(expectedOrder, order);
    }

    @Test
    public void shouldGetOneOrder() {
        Order order = orderDao.getItem(testId).orElse(null);

        assertEquals(testId, order.getSecureId());
    }

    private Order generateEmptyOrder(String id) {
        Client client = new Client("", "", "", "");
        BigDecimal deliveryCost = new BigDecimal(0);
        BigDecimal subtotalPrice = new BigDecimal(0);

        return new Order(id, client, new Date(), deliveryCost, subtotalPrice, PaymentMethod.CASH, Collections.emptyList());
    }
}

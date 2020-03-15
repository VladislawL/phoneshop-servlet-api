package com.es.phoneshop.services.orderservice;

import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.exceptions.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.services.orderservice.DefaultOrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultOrderServiceTest {
    @Mock
    private OrderDao orderDao;
    @Mock
    private Order order;
    @InjectMocks
    private DefaultOrderService orderService;

    @Before
    public void setup() {
        orderService = DefaultOrderService.getInstance();
        orderService.setOrderDao(orderDao);
    }

    @Test
    public void shouldPlaceOrder() {
        orderService.placeOrder(order);

        verify(order).setSecureId(Mockito.anyString());
        verify(orderDao).save(Mockito.eq(order));
    }

    @Test
    public void shouldGetOrder() {
        String id = "1";
        Order expectedOrder = new Order();
        expectedOrder.setSecureId(id);

        when(orderDao.getItem(Mockito.anyString())).thenReturn(Optional.of(expectedOrder));

        Order testOrder = orderService.getOrder(id);

        assertEquals(expectedOrder, testOrder);
    }

    @Test(expected = OrderNotFoundException.class)
    public void shouldThrowOrderNotFoundException() {
        String id = "1";

        when(orderDao.getItem(Mockito.anyString())).thenReturn(Optional.ofNullable(null));

        orderService.getOrder(id);
    }
}

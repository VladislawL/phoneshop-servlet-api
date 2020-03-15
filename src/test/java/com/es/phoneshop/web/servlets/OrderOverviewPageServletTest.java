package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.utils.PriceFormatUtils;
import com.es.phoneshop.services.orderservice.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderOverviewPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private OrderService orderService;
    @InjectMocks
    private OrderOverviewPageServlet servlet = new OrderOverviewPageServlet();
    @Captor
    private ArgumentCaptor<Order> orderArgumentCaptor;
    @Captor
    private ArgumentCaptor<String> totalPriceArgumentCaptor;

    @Before
    public void setup() {
        when(request.getRequestDispatcher(Mockito.anyString())).thenReturn(requestDispatcher);
        when(request.getLocale()).thenReturn(Locale.getDefault());
    }

    @Test
    public void shouldForwardToOverviewPage() throws ServletException, IOException {
        BigDecimal subtotalPrice = new BigDecimal(1);
        String deliveryCostString = "0";
        BigDecimal deliveryCost = new BigDecimal(Integer.parseInt(deliveryCostString));
        BigDecimal totalPrice = subtotalPrice.add(deliveryCost);
        String formattedTotalPrice = PriceFormatUtils.format(totalPrice, Locale.getDefault());

        Order order = new Order();
        String id = "1";
        order.setSecureId(id);
        order.setSubtotalPrice(subtotalPrice);
        order.setDeliveryCost(deliveryCost);

        String path = "/1";

        when(request.getPathInfo()).thenReturn(path);
        when(orderService.getOrder(Mockito.eq(id))).thenReturn(order);

        servlet.doGet(request, response);

        verify(request).setAttribute(Mockito.eq("order"), orderArgumentCaptor.capture());
        assertEquals(id, orderArgumentCaptor.getValue().getSecureId());
        verify(request).setAttribute(Mockito.eq("totalPrice"), totalPriceArgumentCaptor.capture());
        assertEquals(formattedTotalPrice, totalPriceArgumentCaptor.getValue());
    }

    @Test
    public void shouldForwardRequestToDoGet() throws ServletException, IOException {
        OrderOverviewPageServlet orderOverviewPageServlet = Mockito.spy(servlet);

        doNothing().when(orderOverviewPageServlet).doGet(request, response);

        orderOverviewPageServlet.doPost(request, response);

        verify(orderOverviewPageServlet).doGet(request, response);
    }
}

package com.es.phoneshop.web.servlets;

import com.es.phoneshop.exceptions.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.utils.PriceFormatUtils;
import com.es.phoneshop.services.orderservice.DefaultOrderService;
import com.es.phoneshop.services.orderservice.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;

public class OrderOverviewPageServlet extends HttpServlet {
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        orderService = DefaultOrderService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id = request.getPathInfo().substring(1);
            Locale locale = request.getLocale();

            Order order = orderService.getOrder(id);
            BigDecimal subtotalPrice = order.getSubtotalPrice();
            BigDecimal deliveryCost = order.getDeliveryCost();
            BigDecimal totalPrice = subtotalPrice.add(deliveryCost);

            request.setAttribute("order", order);
            request.setAttribute("subtotalPrice", PriceFormatUtils.format(subtotalPrice, locale));
            request.setAttribute("deliveryCost", PriceFormatUtils.format(deliveryCost, locale));
            request.setAttribute("totalPrice", PriceFormatUtils.format(totalPrice, locale));

            request.getRequestDispatcher("/WEB-INF/pages/overviewPage.jsp").forward(request, response);
        } catch (OrderNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

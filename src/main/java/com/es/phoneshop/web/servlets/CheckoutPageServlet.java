package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.client.Client;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.PaymentMethod;
import com.es.phoneshop.model.utils.PriceFormatUtils;
import com.es.phoneshop.services.cartsevice.CartService;
import com.es.phoneshop.services.cartsevice.DefaultCartService;
import com.es.phoneshop.services.orderservice.DefaultOrderService;
import com.es.phoneshop.services.orderservice.OrderService;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CheckoutPageServlet extends HttpServlet {
    private CartService cartService;
    private OrderService orderService;
    private final String FIELD_REQUIRED_ERROR = "Field is required";

    @Override
    public void init() throws ServletException {
        cartService = DefaultCartService.getInstance();
        orderService = DefaultOrderService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);

        if (!cart.getCartItems().isEmpty()) {
            Locale locale = request.getLocale();
            BigDecimal deliveryCost = getDeliveryCostPrice(request);
            BigDecimal subtotalPrice = cart.getSubtotalPrice();
            BigDecimal totalPrice = subtotalPrice.add(deliveryCost);

            request.setAttribute("cart", cart);
            request.setAttribute("subtotalPrice", PriceFormatUtils.format(cart.getSubtotalPrice(), locale));
            request.setAttribute("deliveryCost", PriceFormatUtils.format(deliveryCost, locale));
            request.setAttribute("totalPrice", PriceFormatUtils.format(totalPrice, locale));
            request.getRequestDispatcher("/WEB-INF/pages/checkoutPage.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Your cart is empty");
            request.getRequestDispatcher("/WEB-INF/pages/cartPage.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        BigDecimal deliveryCost = getDeliveryCostPrice(request);
        BigDecimal subtotalPrice = cart.getSubtotalPrice();
        List<CartItem> products = new ArrayList<>(cart.getCartItems());
        Map<String, String> errors = new HashMap<>();

        Client client = getClientFromRequest(request, errors);
        Date deliveryDate = getDeliveryDateFromRequest(request, errors);
        PaymentMethod paymentMethod = getPaymentMethod(request.getParameter("paymentMethod"), errors);

        if (errors.isEmpty()) {
            response.setContentType("text/plain");

            Order order = new Order(client, deliveryDate, deliveryCost, subtotalPrice, paymentMethod, products);
            orderService.placeOrder(order);
            cartService.deleteCart(request);

            String id = order.getSecureId();
            String URL = "order/overview/" + id;
            response.getWriter().write(URL);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");

            JSONObject errorMessage = new JSONObject(errors);

            response.getWriter().write(errorMessage.toString());
        }
    }

    private PaymentMethod getPaymentMethod(String paymentMethod, Map<String, String> errors) {
        Map<String, PaymentMethod> paymentMethods = DefaultOrderService.getPaymentMethodMap();
        if (paymentMethods.containsKey(paymentMethod.toUpperCase())) {
            return paymentMethods.get(paymentMethod.toUpperCase());
        } else {
            errors.put("paymentMethod", FIELD_REQUIRED_ERROR);
            return null;
        }
    }

    private Client getClientFromRequest(HttpServletRequest request, Map<String, String> errors) {
        String firstName = getParameterFromRequest(request,"firstName", errors);
        String lastName = getParameterFromRequest(request,"lastName", errors);
        String phoneNumber = getParameterFromRequest(request,"phoneNumber", errors);
        String address = getParameterFromRequest(request,"address", errors);

        return new Client(firstName, lastName, phoneNumber, address);
    }

    private String getParameterFromRequest(HttpServletRequest request, String parameter, Map<String, String> errors) {
        String result = request.getParameter(parameter);

        if("".equals(result)) {
            errors.put(parameter, FIELD_REQUIRED_ERROR);
        }

        return result;
    }

    private Date getDeliveryDateFromRequest(HttpServletRequest request, Map<String, String> errors) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date deliveryDate = formatter.parse(request.getParameter("deliveryDate"));
            return deliveryDate;
        } catch (ParseException e) {
            errors.put("deliveryDate", "Wrong date");
            return null;
        }
    }

    private BigDecimal getDeliveryCostPrice(HttpServletRequest request) {
        return new BigDecimal(Integer.parseInt(request.getServletContext().getInitParameter("deliveryCost")));
    }
}

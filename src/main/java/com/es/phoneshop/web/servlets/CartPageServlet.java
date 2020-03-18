package com.es.phoneshop.web.servlets;

import com.es.phoneshop.exceptions.ProcessCartException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.exceptions.NegativeQuantityException;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.utils.PriceFormatUtils;
import com.es.phoneshop.services.cartsevice.CartService;
import com.es.phoneshop.services.cartsevice.DefaultCartService;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.ParseException;

public class CartPageServlet extends HttpServlet {
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();
        cartService = DefaultCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);

        request.setAttribute("cart", cart);
        request.setAttribute("subtotalPrice", PriceFormatUtils.format(cart.getSubtotalPrice(), request.getLocale()));
        request.getRequestDispatcher("WEB-INF/pages/cartPage.jsp").forward(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        try {
            Cart cart = cartService.getCart(request);
            JSONObject jsonObject = readJsonFromRequest(request);
            long quantity = getQuantity(request, jsonObject.get("quantity").toString());
            long id = Long.parseLong(jsonObject.get("id").toString());

            updateCart(cart, id, quantity);
            response.getWriter().write(PriceFormatUtils.format(cart.getSubtotalPrice(), request.getLocale()));
        } catch (ProcessCartException e) {
            response.setStatus(e.getStatusCode());
            response.getWriter().write(e.getMessage());
        } catch (ParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("There was a mistake in the number");
        } catch (org.json.simple.parser.ParseException e) {
            getServletContext().log(e.getMessage(), e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("text/plain");

            JSONObject jsonObject = readJsonFromRequest(request);
            long id = Long.parseLong(jsonObject.get("id").toString());
            Cart cart = cartService.getCart(request);

            cartService.delete(cart, id);

            response.getWriter().write(PriceFormatUtils.format(cart.getSubtotalPrice(), request.getLocale()));
        } catch (org.json.simple.parser.ParseException e) {
            getServletContext().log(e.getMessage(), e);
        }
    }

    private long getQuantity(HttpServletRequest request, String quantity) throws ParseException {
        NumberFormat format = NumberFormat.getInstance(request.getLocale());
        return format.parse(quantity).longValue();
    }

    private JSONObject readJsonFromRequest(HttpServletRequest request) throws IOException, org.json.simple.parser.ParseException {
        JSONParser jsonParser = new JSONParser();
        return (JSONObject) jsonParser.parse(IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8));
    }

    private void updateCart(Cart cart, long id, long quantity) {
        try {
            cartService.update(cart, id, quantity);
        } catch (NegativeQuantityException e) {
            throw new ProcessCartException(HttpServletResponse.SC_BAD_REQUEST, "Quantity cannot be negative");
        } catch (OutOfStockException e) {
            throw new ProcessCartException(HttpServletResponse.SC_BAD_REQUEST, "Not enough stock, available " + e.getProduct().getStock());
        }
    }
}

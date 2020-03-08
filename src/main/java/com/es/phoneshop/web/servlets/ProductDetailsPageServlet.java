package com.es.phoneshop.web.servlets;

import com.es.phoneshop.exceptions.ProcessCartException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.exceptions.NegativeQuantityException;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.services.DefaultCartService;
import com.es.phoneshop.services.DefaultProductService;
import com.es.phoneshop.services.RecentlyViewedProductsService;
import com.es.phoneshop.services.ViewedProductsService;
import org.json.simple.JSONObject;

public class ProductDetailsPageServlet extends HttpServlet {
    private DefaultProductService productService;
    private DefaultCartService cartService;
    private ViewedProductsService viewedProductsService;

    @Override
    public void init() throws ServletException {
        super.init();
        productService = DefaultProductService.getInstance();
        cartService = DefaultCartService.getInstance();
        viewedProductsService = RecentlyViewedProductsService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forwardToProductDetailsPage(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        try {
            Cart cart = cartService.getCart(request);
            long quantity = getQuantity(request);
            long id = Long.parseLong(request.getPathInfo().substring(1));

            addToCart(cart, id, quantity);

            response.setContentType("application/json");
            String successMessage = successJsonObject("Added to cart successfully", cart.getCartItems().size());
            response.getWriter().write(successMessage);
        } catch (ParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("There was a mistake in the number");
        } catch (ProcessCartException e) {
            response.setStatus(e.getStatusCode());
            response.getWriter().write(e.getMessage());
        }
    }

    private void forwardToProductDetailsPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long id = Long.valueOf(request.getPathInfo().substring(1));
            Cart cart = cartService.getCart(request);

            Product product = productService.getProductById(id);
            viewedProductsService.addProduct(request, product);

            request.setAttribute("cart", cart);
            request.setAttribute("product", product);

            request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
        } catch (ProductNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private long getQuantity(HttpServletRequest request) throws ParseException {
        NumberFormat format = NumberFormat.getInstance(request.getLocale());
        return format.parse(request.getParameter("quantity")).longValue();
    }

    private void addToCart(Cart cart, long id, long quantity) {
        try {
            cartService.add(cart, id, quantity);
        } catch (NegativeQuantityException e) {
            throw new ProcessCartException(HttpServletResponse.SC_BAD_REQUEST, "Quantity cannot be negative");
        } catch (OutOfStockException e) {
            throw new ProcessCartException(HttpServletResponse.SC_BAD_REQUEST, "Not enough stock, available " + e.getProduct().getStock());
        }
    }

    private String successJsonObject(String message, int numberOfCartItems) {
        JSONObject result = new JSONObject();
        result.put("message", message);
        result.put("numberOfCartItems", numberOfCartItems);
        return result.toString();
    }
}

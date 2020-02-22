package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.NegativeQuantityException;
import com.es.phoneshop.model.cart.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

import com.es.phoneshop.model.product.ProductNotFoundException;
import com.es.phoneshop.services.DefaultCartService;
import com.es.phoneshop.services.DefaultProductService;
import com.es.phoneshop.services.RecentlyViewedProductsService;
import com.es.phoneshop.services.ViewedProductsService;

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
        try {
            Cart cart = cartService.getCart(request);
            long  quantity = getQuantity(request);
            Long id = Long.valueOf(request.getPathInfo().substring(1));

            cartService.add(cart, id, quantity);

            response.sendRedirect(request.getRequestURI() + "?success=true");
        } catch (ParseException e) {
            request.setAttribute("error", "There was a mistake in the number");
            forwardToProductDetailsPage(request, response);
        } catch (OutOfStockException e) {
            request.setAttribute("error", "Not enough stock, available " + e.getProduct().getStock());
            forwardToProductDetailsPage(request, response);
        } catch (NegativeQuantityException e) {
            request.setAttribute("error", "Quantity cannot be negative");
            forwardToProductDetailsPage(request, response);
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
}

package com.es.phoneshop.web.filters;

import com.es.phoneshop.services.cartsevice.CartService;
import com.es.phoneshop.services.cartsevice.DefaultCartService;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CartQuantityFilter implements Filter {
    private CartService cartService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        cartService = DefaultCartService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        int cartQuantity = cartService.getCart(request).getCartItems().size();

        request.setAttribute("cartQuantity", cartQuantity);

        chain.doFilter(request, res);
    }

    @Override
    public void destroy() {

    }
}

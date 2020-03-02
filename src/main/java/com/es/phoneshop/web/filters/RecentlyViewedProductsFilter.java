package com.es.phoneshop.web.filters;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.services.RecentlyViewedProductsService;
import com.es.phoneshop.services.ViewedProductsService;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public class RecentlyViewedProductsFilter implements Filter {
    private ViewedProductsService viewedProductsService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        viewedProductsService = RecentlyViewedProductsService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        List<Product> recentlyViewedProducts = viewedProductsService.getNViewedProducts(req, 3);
        req.setAttribute("recentlyViewedProducts", recentlyViewedProducts);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}

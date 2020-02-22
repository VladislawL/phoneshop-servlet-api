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
import java.util.stream.Collectors;

public class ProductDetailsFilter implements Filter {
    private ViewedProductsService viewedProductsService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        viewedProductsService = RecentlyViewedProductsService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        Long id = Long.valueOf(req.getPathInfo().substring(1));

        List<Product> recentlyViewedProducts = filterRecentlyViewedProducts(viewedProductsService.getProducts(req), id);
        req.setAttribute("recentlyViewedProducts", recentlyViewedProducts);

        chain.doFilter(request, response);
    }

    private List<Product> filterRecentlyViewedProducts(List<Product> products, long id) {
        List<Product> result = products.stream()
                .filter(product -> product.getId() != id)
                .collect(Collectors.toList());
        if(result.size() > 3)
            result = result.subList(0, 3);
        return result;
    }

    @Override
    public void destroy() {
    }
}

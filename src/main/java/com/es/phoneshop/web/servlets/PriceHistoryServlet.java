package com.es.phoneshop.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.services.DefaultProductService;

public class PriceHistoryServlet extends HttpServlet {
    private DefaultProductService productService;

    @Override
    public void init() throws ServletException {
        super.init();
        productService = DefaultProductService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long id = Long.valueOf(request.getPathInfo().substring(1));

            Product product = productService.getProductById(id);

            request.setAttribute("product", product);
            request.setAttribute("today", new Date());
            request.getRequestDispatcher("/WEB-INF/pages/productPriceHistory.jsp").forward(request, response);
        } catch (ProductNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

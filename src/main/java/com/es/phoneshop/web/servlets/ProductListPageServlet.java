package com.es.phoneshop.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.es.phoneshop.services.productservice.DefaultProductService;

public class ProductListPageServlet extends HttpServlet {
    private DefaultProductService productService;

    @Override
    public void init() throws ServletException {
        super.init();
        productService = DefaultProductService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        String field = request.getParameter("field");
        String order = request.getParameter("order");

        request.setAttribute("products", productService.findProducts(query, field, order));

        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}

package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.product.PhoneType;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.utils.ProductUtils;
import com.es.phoneshop.services.productservice.DefaultProductService;
import com.es.phoneshop.services.productservice.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdvancedSearchServlet extends HttpServlet {
    private ProductService productService;
    private final String NUMBER_FORMAT_EXCEPTION_MESSAGE = "Not a number";

    @Override
    public void init() throws ServletException {
        productService = DefaultProductService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>();

        String description = request.getParameter("description");
        BigDecimal minPrice = getMinPrice(request, errors);
        BigDecimal maxPrice = getMaxPrice(request, errors);
        String type = request.getParameter("type");
        String color = request.getParameter("color");
        String operationSystem = request.getParameter("operationSystem");

        List<Product> products = productService.findProducts(description, null, null);

        products = products.stream()
                .filter(ProductUtils.getProductPredicate(minPrice, maxPrice, type, color, operationSystem))
                .collect(Collectors.toList());

        request.setAttribute("products", products);
        request.setAttribute("errors", errors);

        request.getRequestDispatcher("WEB-INF/pages/advancedSearch.jsp").forward(request, response);
    }

    private BigDecimal getMinPrice(HttpServletRequest request, Map<String, String> errors) {
        try {
            String minPrice = request.getParameter("minPrice");
            BigDecimal result = new BigDecimal(Integer.parseInt(minPrice));
            return result;
        } catch (NumberFormatException e) {
            errors.put("minPrice", NUMBER_FORMAT_EXCEPTION_MESSAGE);
            return new BigDecimal(0);
        }
    }

    private BigDecimal getMaxPrice(HttpServletRequest request, Map<String, String> errors) {
        try {
            String maxPrice = request.getParameter("maxPrice");
            BigDecimal result = new BigDecimal(Integer.parseInt(maxPrice));
            return result;
        } catch (NumberFormatException e) {
            errors.put("maxPrice", NUMBER_FORMAT_EXCEPTION_MESSAGE);
            return new BigDecimal(Integer.MAX_VALUE);
        }
    }
}

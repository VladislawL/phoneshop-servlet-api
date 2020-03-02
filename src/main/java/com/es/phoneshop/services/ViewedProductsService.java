package com.es.phoneshop.services;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ViewedProductsService {
    List<Product> getProducts(HttpServletRequest request);
    List<Product> getNViewedProducts(HttpServletRequest request, int n);
    void addProduct(HttpServletRequest request, Product product);
}

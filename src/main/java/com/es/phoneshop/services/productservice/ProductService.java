package com.es.phoneshop.services.productservice;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.exceptions.ProductNotFoundException;

import java.util.List;

public interface ProductService {
    List<Product> findProducts(String description, String field, String order);
    Product getProductById(Long id) throws ProductNotFoundException;
}

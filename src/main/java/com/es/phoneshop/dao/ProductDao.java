package com.es.phoneshop.dao;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.SearchProductResult;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    List<Product> getProducts();
    Optional<Product> getProduct(Long id);
    List<SearchProductResult> findProducts(String description);
    void save(Product product);
    void delete(Long id);
}

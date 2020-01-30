package com.es.phoneshop.model.product;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface ProductDao {
    List<Product> getProducts();
    Optional<Product> getProduct(Long id);
    List<Product> findProducts(Predicate<Product> predicate);
    void save(Product product);
    void delete(Long id);
}

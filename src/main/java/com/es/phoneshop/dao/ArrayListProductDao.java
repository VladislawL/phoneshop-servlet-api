package com.es.phoneshop.dao;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.SearchProductResult;

import static com.es.phoneshop.model.utils.ProductUtils.countNumberOfDescriptionMatches;

public class ArrayListProductDao implements ProductDao {
    private static ArrayListProductDao instance;
    private List<Product> products = new ArrayList<>();
    private long maxId = 1L;

    private ArrayListProductDao() {
    }

    public static synchronized ArrayListProductDao getInstance() {
        if (instance == null) {
            instance = new ArrayListProductDao();
        }
        return instance;
    }

    public synchronized <Long> Optional<Product> getItem(Long id) {
        return products.stream()
                .filter(product -> id.equals(product.getId()))
                .findFirst();
    }

    @Override
    public synchronized List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    @Override
    public synchronized List<SearchProductResult> findProducts(String description) {
        return getRealProducts().stream()
                .map(product -> new SearchProductResult(product, countNumberOfDescriptionMatches(product, description)))
                .filter(searchProductResult -> searchProductResult.getCountOfMatches() > 0)
                .collect(Collectors.toList());
    }

    private List<Product> getRealProducts() {
        return products.stream()
                .filter(product -> product.getPrice() != null)
                .filter(product -> product.getPrice().compareTo(new BigDecimal(0)) != -1)
                .filter(product -> product.getStock() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        if (product.getId() == null) {
            addProduct(product);
        } else {
            updateProduct(product);
        }
    }

    private synchronized void addProduct(Product product) {
        products.add(new Product(maxId++, product.getCode(), product.getDescription(), product.getPrice(), product.getCurrency(), product.getStock(), product.getImageUrl()));
    }

    private synchronized void updateProduct(Product product) {
        products.stream()
                .filter(p -> product.getId().equals(p.getId()))
                .forEach(p -> update(p, product));
    }

    private void update(Product oldProduct, Product newProduct) {
        oldProduct.setCode(newProduct.getCode());
        oldProduct.setDescription(newProduct.getDescription());
        oldProduct.setPrice(newProduct.getPrice());
        oldProduct.setCurrency(newProduct.getCurrency());
        oldProduct.setStock(newProduct.getStock());
        oldProduct.setImageUrl(newProduct.getImageUrl());
    }

    @Override
    public synchronized void delete(Long id) {
        products.removeIf(product -> product.getId().equals(id));
    }
}

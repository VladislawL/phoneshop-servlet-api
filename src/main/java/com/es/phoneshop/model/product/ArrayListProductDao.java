package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private List<Product> products = new ArrayList<>();
    private long maxId = 1L;

    public ArrayListProductDao() {
        generateSampleProducts();
    }

    @Override
    public synchronized Optional<Product> getProduct(Long id) {
        if (id > maxId || id == null) {
            return Optional.empty();
        }
        return products.stream()
                .filter(product -> id.equals(product.getId()))
                .findFirst();
    }

    @Override
    public synchronized List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    @Override
    public synchronized List<Product> findProducts(Predicate<Product> predicate) {
        return products.stream()
                .filter(product -> product.getPrice() != null)
                .filter(product -> product.getPrice().compareTo(new BigDecimal(0)) != -1)
                .filter(product -> product.getStock() > 0)
                .filter(predicate)
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

    private void generateSampleProducts() {
        Currency usd = Currency.getInstance("USD");
        save(new Product(null, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        save(new Product(null, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        save(new Product(null, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        save(new Product(null, "iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        save(new Product(null, "iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        save(new Product(null, "htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        save(new Product(null, "sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        save(new Product(null, "xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        save(new Product(null, "nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
        save(new Product(null, "palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
        save(new Product(null, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
        save(new Product(null, "simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
        save(new Product(null, "simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));
    }
}

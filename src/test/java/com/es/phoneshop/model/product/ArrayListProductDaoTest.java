package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void shouldFindProductsNoResults() {
        assertTrue(productDao.findProducts(product -> product.getId() < 1).isEmpty());
    }

    @Test
    public void shouldSaveOneProduct() {
        Currency usd = Currency.getInstance("USD");
        String description = "Nokia";

        productDao.save(new Product(null, "sgs", description, new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));

        assertFalse(productDao.findProducts(product -> description.equals(product.getDescription())).isEmpty());
    }

    @Test
    public void shouldUpdateOneProduct() {
        Currency usd = Currency.getInstance("USD");
        String description = "IPhone 9";
        Product product = new Product(1L, "sgs", description, new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        productDao.save(product);

        assertEquals(description, productDao.getProduct(1L).get().getDescription());
    }

    @Test
    public void shouldGetProductById() {
        Currency usd = Currency.getInstance("USD");

        productDao.save(new Product(null, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));

        assertNotNull(productDao.getProduct(1L));
    }

    @Test
    public void shouldDeleteProductById() {
        assertNotNull(productDao.getProduct(1L));

        productDao.delete(1L);

        assertEquals(Optional.empty(), productDao.getProduct(1L));
    }
}

package com.es.phoneshop.model.product;

import com.es.phoneshop.dao.ProductDao;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import com.es.phoneshop.dao.ArrayListProductDao;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
    }

    @Test
    public void shouldFindProductsNoResults() {
        assertFalse(productDao.findProducts("").isEmpty());
    }

    @Test
    public void shouldSaveOneProduct() {
        Currency usd = Currency.getInstance("USD");
        String description = "Nokia";

        productDao.save(new Product(null, "sgs", description, new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));

        assertFalse(productDao.findProducts(description).isEmpty());
    }

    @Test
    public void shouldUpdateOneProduct() {
        Currency usd = Currency.getInstance("USD");
        String description = "IPhone 9";
        String newDescription = "Samsung";
        Product testProduct = new Product(null, "sgs", description, new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        Product updateProduct = new Product(1L, "sgs", newDescription, new BigDecimal(100), usd, 100, "");
        productDao.save(testProduct);
        productDao.save(updateProduct);

        assertEquals(newDescription, productDao.getItem(1L).get().getDescription());
    }

    @Test
    public void shouldGetProductById() {
        Currency usd = Currency.getInstance("USD");

        productDao.save(new Product(null, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));

        assertNotNull(productDao.getItem(1L));
    }

    @Test
    public void shouldDeleteProductById() {
        assertNotNull(productDao.getItem(1L));

        productDao.delete(1L);

        assertEquals(Optional.empty(), productDao.getItem(1L));
    }

    @Test
    public void shouldReturnProductList() {
        Currency usd = Currency.getInstance("USD");

        productDao.save(new Product(null, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        List<Product> products = productDao.getProducts();

        assertFalse(products.isEmpty());
    }
}

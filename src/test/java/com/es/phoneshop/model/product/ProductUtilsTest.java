package com.es.phoneshop.model.product;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.model.utils.ProductUtils;
import com.es.phoneshop.services.DefaultProductService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Currency;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ProductUtilsTest {
    private ProductDao productDao;
    private DefaultProductService productService;
    private Comparator<Product> comparator;
    private Product product1;
    private Product product2;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
        productService = DefaultProductService.getInstance();

        Currency usd = Currency.getInstance("USD");
        BigDecimal price1 = new BigDecimal(100);
        BigDecimal price2 = new BigDecimal(200);
        product1 = new Product(null, "sgs", "Samsung", price1, usd, 100, "image");
        product2 = new Product(null, "sgs2", "iPhone", price2, usd, 0, "image");
    }

    @Test
    public void shouldFindProductByDescriptionWithOneMatch() {
        Currency usd = Currency.getInstance("USD");
        String description = "Xiaomi";
        String field = "description";
        String order = "asc";
        productDao.save(new Product(null, "sgs", description, new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));

        Product product = productService.findProducts(description, field, order).get(0);

        assertEquals(description, product.getDescription());
    }

    @Test
    public void shouldCompareProductsByPriceInAscending() {
        comparator = ProductUtils.getComparator(SortField.PRICE, SortOrder.ASCENDING);

        assertEquals(1, comparator.compare(product1, product2));
        assertNotEquals(1, comparator.compare(product2, product1));
    }

    @Test
    public void shouldCompareProductsByPriceInDescending() {
        comparator = ProductUtils.getComparator(SortField.PRICE, SortOrder.DESCENDING);

        assertEquals(-1, comparator.compare(product1, product2));
        assertNotEquals(-1, comparator.compare(product2, product1));
    }

    @Test
    public void shouldCompareProductsByDescriptionInAscending() {
        comparator = ProductUtils.getComparator(SortField.DESCRIPTION, SortOrder.ASCENDING);

        assertTrue(comparator.compare(product1, product2) < 0);
        assertTrue(comparator.compare(product2, product1) > 0);
    }

    @Test
    public void shouldCompareProductsByDescriptionInDescending() {
        comparator = ProductUtils.getComparator(SortField.DESCRIPTION, SortOrder.DESCENDING);

        assertTrue(comparator.compare(product1, product2) > 0);
        assertTrue(comparator.compare(product2, product1) < 0);
    }
}

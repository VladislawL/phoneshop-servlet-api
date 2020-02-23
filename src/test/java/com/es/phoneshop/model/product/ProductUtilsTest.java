package com.es.phoneshop.model.product;

import com.es.phoneshop.model.utils.ProductUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Currency;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(MockitoJUnitRunner.class)
public class ProductUtilsTest {
    private Comparator<Product> comparator;
    private Product product1;
    private Product product2;

    @Before
    public void setup() {
        Currency usd = Currency.getInstance("USD");
        BigDecimal price1 = new BigDecimal(100);
        BigDecimal price2 = new BigDecimal(200);
        product1 = new Product(1L, "sgs", "Samsung", price1, usd, 100, "image");
        product2 = new Product(2L, "sgs2", "iPhone", price2, usd, 0, "image");
    }

    @Test
    public void shouldFindProductByDescriptionWithOneMatch() {
        Currency usd = Currency.getInstance("USD");
        String description = "Xiaomi Redmi";
        int expectedCountOfMatches = 2;
        Product testProduct = new Product(1L, "", description, new BigDecimal(0), usd, 0, "");

        int countOfMatches = ProductUtils.countNumberOfDescriptionMatches(testProduct, description);

        assertEquals(expectedCountOfMatches, countOfMatches);
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

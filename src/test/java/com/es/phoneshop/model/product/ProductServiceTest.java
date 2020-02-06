package com.es.phoneshop.model.product;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.services.DefaultProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
    @Mock
    private ProductDao productDao;
    private DefaultProductService productService;

    @Before
    public void setup() {
        productService = DefaultProductService.getInstance();
        productService.setProductDao(productDao);
    }

    @Test
    public void shouldFindProductByDescription() {
        String description = "Xiaomi";
        Currency usd = Currency.getInstance("USD");
        SearchProductResult product = new SearchProductResult(new Product(null, "sgs", description, new BigDecimal(100), usd, 100, "image"));
        List<SearchProductResult> products = new ArrayList<>();
        products.add(product);

        when(productDao.findProducts(Mockito.anyString())).thenReturn(products);

        List<Product> productList = productService.findProducts(description,null, null);

        assertEquals(description, productList.get(0).getDescription());
    }

    @Test
    public void shouldGetProductById() {
        String description = "Xiaomi";
        Currency usd = Currency.getInstance("USD");
        Long id = 1L;
        Product testProduct = new Product(id, "sgs", description, new BigDecimal(100), usd, 100, "image");

        when(productDao.getProduct(id)).thenReturn(Optional.of(testProduct));

        Product product = productService.getProductById(id);

        assertNotNull(product);
        assertEquals(Long.valueOf(id), product.getId());
    }

    @Test
    public void shouldSortListInAscending() {
        String field = "description";
        String order = "asc";
        List<SearchProductResult> products = generateSampleProducts();

        when(productDao.findProducts(Mockito.anyString())).thenReturn(products);

        List<Product> sortedProductList = productService.findProducts("", field, order);

        assertTrue(checkProductAscendingSort(sortedProductList));
    }

    private boolean checkProductAscendingSort(List<Product> list) {
        String prev = "";
        for (Product t : list) {
            if (prev.compareTo(t.getDescription()) == 1)
                return false;
        }
        return true;
    }

    private List<SearchProductResult> generateSampleProducts() {
        List<SearchProductResult> productList = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");

        productList.add(new SearchProductResult(new Product(1L, "", "A", new BigDecimal(1), usd, 0, "")));
        productList.add(new SearchProductResult(new Product(2L, "", "B", new BigDecimal(2), usd, 0, "")));
        productList.add(new SearchProductResult(new Product(3L, "", "C", new BigDecimal(3), usd, 0, "")));
        productList.add(new SearchProductResult(new Product(4L, "", "D", new BigDecimal(4), usd, 0, "")));

        return productList;
    }
}

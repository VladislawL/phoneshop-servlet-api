package com.es.phoneshop.web.filters;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.services.viewedproductsservice.RecentlyViewedProductsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsFilterTest {
    @Mock
    private RecentlyViewedProductsService viewedProductsService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;
    @InjectMocks
    ProductDetailsFilter productDetailsFilter;
    @Captor
    private ArgumentCaptor<List<Product>> productsArgumentCaptor;

    @Test
    public void shouldAddRecentlyViewedProductsWithoutCurrent() throws IOException, ServletException {
        int expectedSize = 0;
        Currency usd = Currency.getInstance("USD");
        long productId = 1L;
        List<Product> products = Arrays.asList(new Product(productId, "", "", new BigDecimal(1), usd, 1, ""));
        String path = "/1";

        when(request.getPathInfo()).thenReturn(path);
        when(viewedProductsService.getProducts(request)).thenReturn(products);

        productDetailsFilter.doFilter(request, response, chain);

        verify(request).setAttribute(Mockito.eq("recentlyViewedProducts"), productsArgumentCaptor.capture());
        assertEquals(expectedSize, productsArgumentCaptor.getValue().size());
        verify(chain).doFilter(request, response);
    }
}

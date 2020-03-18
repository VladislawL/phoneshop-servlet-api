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
public class RecentlyViewedProductsFilterTest {
    @Mock
    private RecentlyViewedProductsService viewedProductsService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;
    @InjectMocks
    RecentlyViewedProductsFilter recentlyViewedProductsFilter;
    @Captor
    private ArgumentCaptor<List<Product>> productsArgumentCaptor;

    @Test
    public void shouldAddRecentlyViewedProductsToRequest() throws IOException, ServletException {
        Currency usd = Currency.getInstance("USD");
        long productId = 1L;
        Product expectedProduct = new Product(productId, "", "", new BigDecimal(1), usd, 1, "");
        List<Product> products = Arrays.asList(expectedProduct);

        when(viewedProductsService.getNViewedProducts(request, 3)).thenReturn(products);

        recentlyViewedProductsFilter.doFilter(request, response, chain);

        verify(request).setAttribute(Mockito.eq("recentlyViewedProducts"), productsArgumentCaptor.capture());
        assertEquals(expectedProduct.getId(), productsArgumentCaptor.getValue().get(0).getId());
        verify(chain).doFilter(request, response);
    }
}

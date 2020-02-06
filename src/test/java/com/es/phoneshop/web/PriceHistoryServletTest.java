package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.services.DefaultProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PriceHistoryServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private DefaultProductService productService;
    @InjectMocks
    private PriceHistoryServlet servlet = new PriceHistoryServlet();
    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void shouldDoGet() throws ServletException, IOException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "image");
        String path = "/1";
        when(request.getPathInfo()).thenReturn(path);
        when(productService.getProductById(1L)).thenReturn(product);

        servlet.doGet(request, response);

        verify(request).getPathInfo();
        verify(request).setAttribute(Mockito.eq("product"), productArgumentCaptor.capture());
        assertEquals(product, productArgumentCaptor.getValue());
        verify(requestDispatcher).forward(request, response);
    }
}

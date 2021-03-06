package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.services.productservice.DefaultProductService;
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
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductListPageServletTest {

    @InjectMocks
    private ProductListPageServlet servlet = new ProductListPageServlet();
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private DefaultProductService productService;
    @Captor
    private ArgumentCaptor<List<Product>> productListArgumentCaptor;

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void shouldDoGet() throws ServletException, IOException {
        List<Product> products = Collections.emptyList();
        when(productService.findProducts(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(products);

        servlet.doGet(request, response);

        verify(request).setAttribute(Mockito.eq("products"), productListArgumentCaptor.capture());
        assertEquals(products, productListArgumentCaptor.getValue());
        verify(requestDispatcher).forward(request, response);
    }
}
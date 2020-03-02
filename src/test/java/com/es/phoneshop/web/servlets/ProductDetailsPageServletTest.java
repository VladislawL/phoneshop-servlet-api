package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.services.DefaultCartService;
import com.es.phoneshop.services.DefaultProductService;
import com.es.phoneshop.services.RecentlyViewedProductsService;
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
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private DefaultProductService productService;
    @Mock
    private DefaultCartService cartService;
    @Mock
    private RecentlyViewedProductsService viewedProductsService;
    @InjectMocks
    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();
    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;
    @Captor
    private ArgumentCaptor<String> messageArgumentCaptor;

    private Product product;
    private long productId = 1;

    @Before
    public void setup() {
        Currency usd = Currency.getInstance("USD");
        product = new Product(productId, "", "", new BigDecimal(1), usd, 1, "");
        String path = "/1";

        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getPathInfo()).thenReturn(path);
        when(productService.getProductById(productId)).thenReturn(product);
    }

    @Test
    public void shouldDoGet() throws ServletException, IOException {
        Cart cart = new Cart();

        when(cartService.getCart(request)).thenReturn(cart);

        servlet.doGet(request, response);

        verify(request).getPathInfo();
        verify(request).setAttribute(Mockito.eq("product"), productArgumentCaptor.capture());
        verify(viewedProductsService).addProduct(request, product);
        assertEquals(product, productArgumentCaptor.getValue());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void shouldRedirectIfThereAreNoExceptionsInDoPost() throws ServletException, IOException {
        Cart cart = new Cart();
        Locale locale = Locale.getDefault();
        String quantity = "1";
        String path = "/1";

        when(cartService.getCart(request)).thenReturn(cart);
        when(request.getLocale()).thenReturn(locale);
        when(request.getParameter("quantity")).thenReturn(quantity);

        servlet.doPost(request, response);

        verify(cartService).add(cart, Long.parseLong(path.substring(1)), Integer.parseInt(quantity));
        verify(response).sendRedirect(Mockito.anyString());
    }

    @Test
    public void shouldThrowNumberFormatExceptionInDoPost() throws ServletException, IOException {
        Cart cart = new Cart();
        Locale locale = Locale.getDefault();
        String quantity = "";
        String message = "There was a mistake in the number";

        when(cartService.getCart(request)).thenReturn(cart);
        when(request.getLocale()).thenReturn(locale);
        when(request.getParameter("quantity")).thenReturn(quantity);

        servlet.doPost(request, response);

        verify(request).setAttribute(Mockito.same("error"), messageArgumentCaptor.capture());
        assertEquals(message, messageArgumentCaptor.getValue());
    }

    @Test
    public void shouldThrowOutOfStockExceptionInDoPost() throws ServletException, IOException {
        Cart cart = new Cart();
        Locale locale = Locale.getDefault();
        String message = "Not enough stock, available " + product.getStock();
        String stringQuantity = "1";
        long longQuantity = 1L;

        when(cartService.getCart(request)).thenReturn(cart);
        when(request.getLocale()).thenReturn(locale);
        when(request.getParameter("quantity")).thenReturn(stringQuantity);
        doThrow(new OutOfStockException(product, productId)).when(cartService).add(cart, productId, longQuantity);

        servlet.doPost(request, response);

        verify(request).setAttribute(Mockito.same("error"), messageArgumentCaptor.capture());
        assertEquals(message, messageArgumentCaptor.getValue());
    }
}

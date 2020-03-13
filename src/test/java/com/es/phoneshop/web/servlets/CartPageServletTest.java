package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.exceptions.NegativeQuantityException;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.services.DefaultCartService;
import com.es.phoneshop.services.DefaultProductService;
import org.json.simple.JSONObject;
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
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private DefaultCartService cartService;
    @Mock
    private PrintWriter writer;
    @InjectMocks
    private CartPageServlet servlet = new CartPageServlet();
    @Captor
    private ArgumentCaptor<Cart> cartArgumentCaptor;
    @Captor
    private ArgumentCaptor<String> totalPriceArgumentCaptor;
    @Captor
    private ArgumentCaptor<Long> productIdArgumentCaptor;

    @Before
    public void setup() {

        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void shouldDoGet() throws ServletException, IOException {
        List<CartItem> cartItems = Collections.EMPTY_LIST;
        Cart cart = new Cart();
        cart.setCartItems(cartItems);

        when(request.getLocale()).thenReturn(Locale.getDefault());
        when(cartService.getCart(request)).thenReturn(cart);

        servlet.doGet(request, response);

        verify(request).setAttribute(Mockito.eq("cart"), cartArgumentCaptor.capture());
        assertEquals(cart, cartArgumentCaptor.getValue());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void shouldSendTotalPriceIfThereAreNoExceptionsInDoPut() throws ServletException, IOException {
        long quantity = 1;
        long productId = 1;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("quantity", quantity);
        jsonObject.put("id", productId);
        BigDecimal totalPrice = new BigDecimal(1);

        Currency usd = Currency.getInstance("USD");
        Product product = new Product(productId, "", "", new BigDecimal(1), usd, 1, "");
        List<CartItem> cartItems = Arrays.asList(new CartItem(product, 0));

        ServletInputStream inputStream = new TestServletInputStream(jsonObject.toString().getBytes());

        Cart cart = new Cart();
        cart.setCartItems(cartItems);
        cart.setTotalPrice(totalPrice);

        when(cartService.getCart(request)).thenReturn(cart);
        when(request.getInputStream()).thenReturn(inputStream);
        when(request.getLocale()).thenReturn(Locale.getDefault());
        when(response.getWriter()).thenReturn(writer);
        when(cartService.formatTotalPrice(Mockito.eq(cart), Mockito.any())).thenReturn(totalPrice.toString());

        servlet.doPut(request, response);

        verify(writer).write(totalPriceArgumentCaptor.capture());
        assertEquals(totalPrice.toString(), totalPriceArgumentCaptor.getValue());
    }

    @Test
    public void shouldSendParseExceptionMessageInDoPut() throws ServletException, IOException {
        long productId = 1;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("quantity", "e");
        jsonObject.put("id", productId);
        String errorMessage = "There was a mistake in the number";

        ServletInputStream inputStream = new TestServletInputStream(jsonObject.toString().getBytes());

        Cart cart = new Cart();

        when(cartService.getCart(request)).thenReturn(cart);
        when(request.getInputStream()).thenReturn(inputStream);
        when(request.getLocale()).thenReturn(Locale.getDefault());
        when(response.getWriter()).thenReturn(writer);

        servlet.doPut(request, response);

        verify(writer).write(totalPriceArgumentCaptor.capture());
        assertEquals(errorMessage, totalPriceArgumentCaptor.getValue());
    }

    @Test
    public void shouldSendNegativeNumberExceptionMessageInDoPut() throws IOException, ServletException {
        long quantity = -1;
        long productId = 1;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("quantity", quantity);
        jsonObject.put("id", productId);
        String errorMessage = "Quantity cannot be negative";

        ServletInputStream inputStream = new TestServletInputStream(jsonObject.toString().getBytes());

        Cart cart = new Cart();

        when(cartService.getCart(request)).thenReturn(cart);
        when(request.getInputStream()).thenReturn(inputStream);
        when(request.getLocale()).thenReturn(Locale.getDefault());
        doThrow(new NegativeQuantityException(quantity)).when(cartService).update(cart, productId, quantity);
        when(response.getWriter()).thenReturn(writer);

        servlet.doPut(request, response);

        verify(writer).write(totalPriceArgumentCaptor.capture());
        assertEquals(errorMessage, totalPriceArgumentCaptor.getValue());
    }

    @Test
    public void shouldSendOutOfStockExceptionMessageInDoPut() throws IOException, ServletException {
        long quantity = 100;
        long productId = 1;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("quantity", quantity);
        jsonObject.put("id", productId);

        Currency usd = Currency.getInstance("USD");
        Product product = new Product(productId, "", "", new BigDecimal(1), usd, 1, "");

        String errorMessage = "Not enough stock, available " + product.getId();

        ServletInputStream inputStream = new TestServletInputStream(jsonObject.toString().getBytes());

        Cart cart = new Cart();

        when(cartService.getCart(request)).thenReturn(cart);
        when(request.getInputStream()).thenReturn(inputStream);
        when(request.getLocale()).thenReturn(Locale.getDefault());
        doThrow(new OutOfStockException(product, quantity)).when(cartService).update(cart, productId, quantity);
        when(response.getWriter()).thenReturn(writer);

        servlet.doPut(request, response);

        verify(writer).write(totalPriceArgumentCaptor.capture());
        assertEquals(errorMessage, totalPriceArgumentCaptor.getValue());
    }

    @Test
    public void shouldDeleteProductFromCart() throws ServletException, IOException {
        long productId = 1;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", productId);
        BigDecimal totalPrice = new BigDecimal(1);

        Currency usd = Currency.getInstance("USD");
        Product product = new Product(productId, "", "", new BigDecimal(1), usd, 1, "");
        List<CartItem> cartItems = Arrays.asList(new CartItem(product, 0));

        ServletInputStream inputStream = new TestServletInputStream(jsonObject.toString().getBytes());

        Cart cart = new Cart();
        cart.setCartItems(cartItems);
        cart.setTotalPrice(new BigDecimal(0));

        when(cartService.getCart(request)).thenReturn(cart);
        when(request.getInputStream()).thenReturn(inputStream);
        when(response.getWriter()).thenReturn(writer);
        when(cartService.formatTotalPrice(Mockito.eq(cart), Mockito.any())).thenReturn(totalPrice.toString());

        servlet.doDelete(request, response);

        verify(writer).write(totalPriceArgumentCaptor.capture());
        assertEquals(totalPrice.toString(), totalPriceArgumentCaptor.getValue());
        verify(cartService).delete(Mockito.eq(cart), productIdArgumentCaptor.capture());
        assertEquals(productId, productIdArgumentCaptor.getValue().longValue());
    }
}

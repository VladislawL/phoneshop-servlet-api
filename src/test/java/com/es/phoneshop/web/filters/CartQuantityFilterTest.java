package com.es.phoneshop.web.filters;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.services.CartService;
import org.junit.Before;
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
public class CartQuantityFilterTest {
    @Mock
    private CartService cartService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;
    @InjectMocks
    CartQuantityFilter cartQuantityFilter;
    @Captor
    private ArgumentCaptor<Integer> quantityArgumentCaptor;

    @Test
    public void shouldAddQuantityAttributeToRequest() throws IOException, ServletException {
        Integer expectedQuantityOfProducts = 1;
        Cart cart = new Cart();
        List<CartItem> items = Arrays.asList(new CartItem());
        cart.setCartItems(items);

        when(cartService.getCart(request)).thenReturn(cart);

        cartQuantityFilter.doFilter(request, response, chain);

        verify(request).setAttribute(Mockito.eq("cartQuantity"), quantityArgumentCaptor.capture());
        assertEquals(expectedQuantityOfProducts, quantityArgumentCaptor.getValue());
        verify(chain).doFilter(request, response);
    }
}

package com.es.phoneshop.model.product;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.services.DefaultCartService;
import com.es.phoneshop.services.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCartServiceTest {

    @InjectMocks
    private DefaultCartService cartService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private ProductService productService;
    @Mock
    private Cart cart;
    @Mock
    private List<CartItem> items;
    @Captor
    private ArgumentCaptor<CartItem> cartItemArgumentCaptor;

    private String cartAttribute = DefaultCartService.CART_ATTRIBUTE;

    @Before
    public void startup() {
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void shouldGetCartFromSession() {
        when(session.getAttribute(Mockito.same(cartAttribute))).thenReturn(cart);

        Cart testCart = cartService.getCart(request);

        assertEquals(testCart, cart);
    }

    @Test
    public void shouldAddProductToCart() {
        long productId = 1;
        long quantity = 1;
        Currency usd = Currency.getInstance("USD");
        Product product = new Product(productId, "", "", new BigDecimal(1), usd, 1, "");


        when(productService.getProductById(productId)).thenReturn(product);
        when(cart.getCartItems()).thenReturn(items);
        when(items.iterator()).thenReturn(Collections.emptyIterator());

        cartService.add(cart, productId, quantity);

        verify(items).add(cartItemArgumentCaptor.capture());
        assertEquals(productId, cartItemArgumentCaptor.getValue().getProductId());
    }


}

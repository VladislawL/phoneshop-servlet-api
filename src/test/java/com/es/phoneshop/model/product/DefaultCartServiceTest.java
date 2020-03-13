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
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Currency;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

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
    @Captor
    private ArgumentCaptor<CartItem> cartItemArgumentCaptor;

    private String cartAttribute = DefaultCartService.CART_ATTRIBUTE;
    private Cart cart;
    private List<CartItem> cartItems;

    @Before
    public void setup() {
        cart = new Cart();
        cartItems = new LinkedList<>();
        cart.setCartItems(cartItems);

        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void shouldGetCartFromSession() {
        Cart cart = new Cart();

        when(session.getAttribute(Mockito.same(cartAttribute))).thenReturn(cart);

        Cart expectedCart = cartService.getCart(request);

        assertEquals(expectedCart, cart);
    }

    @Test
    public void shouldAddProductToCart() {
        long productId = 1;
        long quantity = 1;
        Currency usd = Currency.getInstance("USD");
        Product product = new Product(productId, "", "", new BigDecimal(1), usd, 1, "");

        when(productService.getProductById(productId)).thenReturn(product);

        cartService.add(cart, productId, quantity);

        CartItem expectedCartItem = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                .findFirst().orElse(null);
        assertEquals(productId, expectedCartItem.getProduct().getId().longValue());
    }

    @Test
    public void shouldDeleteCartItemFromCart() {
        long productId = 1;
        long quantity = 1;
        Currency usd = Currency.getInstance("USD");
        Product product = new Product(productId, "", "", new BigDecimal(1), usd, 1, "");
        cartItems.add(new CartItem(product, quantity));

        cartService.delete(cart, productId);

        assertTrue(cart.getCartItems().isEmpty());
    }

    @Test
    public void shouldUpdateCartItemInCart() {
        long productId = 1;
        long oldQuantity = 1;
        long newQuantity = 5;
        Currency usd = Currency.getInstance("USD");
        Product product = new Product(productId, "", "", new BigDecimal(1), usd, (int) newQuantity, "");
        cartItems.add(new CartItem(product, oldQuantity));

        when(productService.getProductById(productId)).thenReturn(product);

        cartService.update(cart, productId, newQuantity);

        CartItem expectedCartItem = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                .findFirst().orElse(null);
        assertEquals(newQuantity, expectedCartItem.getQuantity());
    }

    @Test
    public void shouldFormatTotalPrice() {
        Locale locale = Locale.getDefault();
        Currency currency = Currency.getInstance("USD");

        Cart cart = new Cart();
        BigDecimal testTotalPrice = new BigDecimal(0);
        cart.setTotalPrice(testTotalPrice);

        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        formatter.setCurrency(currency);
        String expectedTotalPrice = formatter.format(testTotalPrice);

        String totalPrice = cartService.formatTotalPrice(cart, locale);

        assertEquals(expectedTotalPrice, totalPrice);
    }
}

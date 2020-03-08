package com.es.phoneshop.services;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.exceptions.NegativeQuantityException;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class DefaultCartService implements CartService {
    private static DefaultCartService instance;
    private ProductService productService;
    public static final String CART_ATTRIBUTE = "cart_" + DefaultCartService.class;

    private DefaultCartService() {
        productService = DefaultProductService.getInstance();
    }

    public static DefaultCartService getInstance() {
        if (instance == null)
            instance = new DefaultCartService();
        return instance;
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart;

        synchronized (session) {
            cart = (Cart) session.getAttribute(CART_ATTRIBUTE);

            if (cart == null) {
                cart = new Cart();
                session.setAttribute(CART_ATTRIBUTE, cart);
            }
        }

        return cart;
    }

    @Override
    public void add(Cart cart, long productId, long quantity) throws OutOfStockException {
        if (quantity < 0) {
            throw new NegativeQuantityException(quantity);
        }

        synchronized (cart) {
            checkStock(cart, productId, quantity);
            CartItem item = findCartItem(cart, productId);

            if (item == null) {
                List<CartItem> items = cart.getCartItems();
                Product product = productService.getProductById(productId);
                items.add(new CartItem(product, quantity));
                calculateTotalPrice(cart);
            } else {
                item.setQuantity(item.getQuantity() + quantity);
            }
        }
    }

    @Override
    public void update(Cart cart, long productId, long quantity) {
        if (quantity < 0) {
            throw new NegativeQuantityException(quantity);
        }

        synchronized (cart) {
            CartItem item = findCartItem(cart, productId);

            if (item == null) {
                add(cart, productId, quantity);
            } else {
                checkStock(productId, quantity);
                item.setQuantity(quantity);
                calculateTotalPrice(cart);
            }
        }
    }

    @Override
    public void delete(Cart cart, long productId) {
        synchronized (cart) {
            List<CartItem> cartItems = cart.getCartItems();

            cartItems.removeIf(cartItem -> cartItem.getProduct().getId().equals(productId));

            calculateTotalPrice(cart);
        }
    }

    @Override
    public String formatTotalPrice(Cart cart, Locale locale) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        Currency currency = Currency.getInstance("USD");
        formatter.setCurrency(currency);
        return formatter.format(cart.getTotalPrice());
    }

    private void checkStock(Cart cart, long productId, long newQuantity) {
        CartItem item = findCartItem(cart, productId);
        long oldQuantity = (item == null) ? 0L : item.getQuantity();
        checkStock(productId, oldQuantity, newQuantity);
    }

    private void checkStock(long productId, long quantity) {
        checkStock(productId, 0, quantity);
    }

    private void checkStock(long productId, long oldQuantity, long newQuantity) {
        Product product = productService.getProductById(productId);

        if (product.getStock() < oldQuantity + newQuantity) {
            throw new OutOfStockException(product, newQuantity);
        }
    }

    private CartItem findCartItem(Cart cart, long productId) {
        List<CartItem> items = cart.getCartItems();

        for (CartItem item : items) {
            if (item.getProduct().getId().equals(productId)) {
                return item;
            }
        }

        return null;
    }

    private void calculateTotalPrice(Cart cart) {
        List<CartItem> items = cart.getCartItems();
        BigDecimal totalPrice = new BigDecimal(0);
        for (CartItem item : items) {
            totalPrice = totalPrice.add(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        cart.setTotalPrice(totalPrice);
    }
}

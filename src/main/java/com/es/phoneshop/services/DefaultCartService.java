package com.es.phoneshop.services;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.cart.NegativeQuantityException;
import com.es.phoneshop.model.cart.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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
        if(quantity < 0) {
            throw new NegativeQuantityException(quantity);
        }

        synchronized (cart) {
            checkStock(cart, productId, quantity);
            CartItem item = findCartItem(cart, productId);

            if (item == null) {
                List<CartItem> items = cart.getCartItems();
                items.add(new CartItem(productId, quantity));
            } else {
                item.setQuantity(item.getQuantity() + quantity);
            }
        }
    }

    private void checkStock(Cart cart, long productId, long quantity) {
        Product product = productService.getProductById(productId);
        CartItem item = findCartItem(cart, productId);
        long itemQuantity = (item == null) ? 0L : item.getQuantity();

        if (product.getStock() < quantity + itemQuantity) {
            throw new OutOfStockException(product, quantity);
        }
    }

    private CartItem findCartItem(Cart cart, long productId) {
        List<CartItem> items = cart.getCartItems();

        for (CartItem item : items) {
            if (item.getProductId() == productId) {
                return item;
            }
        }

        return null;
    }
}

package com.es.phoneshop.services;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.recentlyviewedproducts.ViewedProducts;
import com.es.phoneshop.model.utils.ProductUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class RecentlyViewedProductsService implements ViewedProductsService {
    private static RecentlyViewedProductsService instance;
    public static final String RECENTLY_VIEWED_ATTRIBUTE = "recentlyViewedProducts";
    public static final int MAX_RECENTLY_VIEWED_PRODUCTS = 4;

    private RecentlyViewedProductsService() {
    }

    public static RecentlyViewedProductsService getInstance() {
        if (instance == null)
            instance = new RecentlyViewedProductsService();
        return instance;
    }

    @Override
    public List<Product> getProducts(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ViewedProducts viewedProducts = getViewedProductsFromSession(session);

        return viewedProducts.getProductList();
    }

    @Override
    public List<Product> getNViewedProducts(HttpServletRequest request, int n) {
        HttpSession session = request.getSession();
        ViewedProducts viewedProducts = getViewedProductsFromSession(session);

        if(viewedProducts.getProductList().size() > n) {
            return viewedProducts.getProductList().subList(0, n);
        } else {
            return viewedProducts.getProductList();
        }
    }

    @Override
    public void addProduct(HttpServletRequest request, Product product) {
        HttpSession session = request.getSession();
        List<Product> productList = getViewedProductsFromSession(session).getProductList();
        Optional<Product> oldProduct = ProductUtils.getProductFromList(productList, product.getId());

        if (!oldProduct.isPresent()) {
            productList.add(0, product);

            if (productList.size() > MAX_RECENTLY_VIEWED_PRODUCTS) {
                productList.remove(productList.size() - 1);
            }

            session.setAttribute(RECENTLY_VIEWED_ATTRIBUTE, new ViewedProducts(productList));
        } else {
            productList.remove(oldProduct.get());
            productList.add(0, product);
        }
    }

    private ViewedProducts getViewedProductsFromSession(HttpSession session) {
        ViewedProducts viewedProducts = (ViewedProducts) session.getAttribute(RECENTLY_VIEWED_ATTRIBUTE);

        if (viewedProducts == null) {
            viewedProducts = new ViewedProducts();
            session.setAttribute(RECENTLY_VIEWED_ATTRIBUTE, viewedProducts);
            return viewedProducts;
        }

        return viewedProducts;
    }
}

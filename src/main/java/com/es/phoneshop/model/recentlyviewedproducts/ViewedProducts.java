package com.es.phoneshop.model.recentlyviewedproducts;

import com.es.phoneshop.model.product.Product;

import java.util.LinkedList;
import java.util.List;

public class ViewedProducts {
    private List<Product> productList;

    public ViewedProducts() {
        productList = new LinkedList<>();
    }

    public ViewedProducts(List<Product> productList) {
        this.productList = productList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> viewedProducts) {
        this.productList = viewedProducts;
    }
}

package com.es.phoneshop.model.product;

public class SearchProductResult {
    private Product product;
    private int countOfMatches;

    public SearchProductResult() {
    }

    public SearchProductResult(Product product) {
        this(product, 0);
    }

    public SearchProductResult(Product product, int countOfMatches) {
        this.product = product;
        this.countOfMatches = countOfMatches;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCountOfMatches() {
        return countOfMatches;
    }

    public void setCountOfMatches(int countOfMatches) {
        this.countOfMatches = countOfMatches;
    }
}

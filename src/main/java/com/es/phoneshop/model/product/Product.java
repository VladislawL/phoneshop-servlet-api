package com.es.phoneshop.model.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Product implements Serializable {
    private Long id;
    private String code;
    private String description;
    /**
     * null means there is no price because the product is outdated or new
     */
    private BigDecimal price;
    /**
     * can be null if the price is null
     */
    private Currency currency;
    private int stock;
    private String imageUrl;
    private Map<Date, BigDecimal> previousPrices;
    private PhoneType type;
    private String color;
    private OperationSystem operationSystem;

    public Product() {
    }

    public Product(Long id, String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
        this(id, code, description, price, currency, stock, imageUrl, new HashMap<>());
    }

    public Product(Long id, String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl, Map<Date, BigDecimal> previousPrices) {
        this(id, code, description, price, currency, stock, imageUrl, previousPrices, PhoneType.DEFAULT, "", OperationSystem.DEFAULT);
    }

    public Product(Long id, String code, String description, BigDecimal price, Currency currency,
                   int stock, String imageUrl, Map<Date, BigDecimal> previousPrices, PhoneType type, String color, OperationSystem operationSystem) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.previousPrices = previousPrices;
        this.type = type;
        this.color = color;
        this.operationSystem = operationSystem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Map<Date, BigDecimal> getPreviousPrices() {
        return previousPrices;
    }

    public void setPreviousPrices(Map<Date, BigDecimal> previousPrices) {
        this.previousPrices = previousPrices;
    }

    public PhoneType getType() {
        return type;
    }

    public void setType(PhoneType type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public OperationSystem getOperationSystem() {
        return operationSystem;
    }

    public void setOperationSystem(OperationSystem operationSystem) {
        this.operationSystem = operationSystem;
    }
}
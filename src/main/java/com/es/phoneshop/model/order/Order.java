package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.client.Client;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
    private String secureId;
    private Client client;
    private Date deliveryDate;
    private BigDecimal deliveryCost;
    private BigDecimal subtotalPrice;
    private PaymentMethod paymentMethods;
    private List<CartItem> products;

    public Order() {
    }

    public Order(Client client, Date deliveryDate, BigDecimal deliveryCost, BigDecimal subtotalPrice, PaymentMethod paymentMethods, List<CartItem> products) {
        this("", client, deliveryDate, deliveryCost, subtotalPrice, paymentMethods, products);
    }

    public Order(String secureId, Client client, Date deliveryDate, BigDecimal deliveryCost,
                 BigDecimal subtotalPrice, PaymentMethod paymentMethods, List<CartItem> products) {
        this.secureId = secureId;
        this.client = client;
        this.deliveryDate = deliveryDate;
        this.deliveryCost = deliveryCost;
        this.subtotalPrice = subtotalPrice;
        this.paymentMethods = paymentMethods;
        this.products = products;
    }

    public String getSecureId() {
        return secureId;
    }

    public void setSecureId(String secureId) {
        this.secureId = secureId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public PaymentMethod getPaymentMethods() {
        return paymentMethods;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public void setPaymentMethods(PaymentMethod paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public List<CartItem> getProducts() {
        return products;
    }

    public BigDecimal getSubtotalPrice() {
        return subtotalPrice;
    }

    public void setSubtotalPrice(BigDecimal subtotalPrice) {
        this.subtotalPrice = subtotalPrice;
    }

    public void setProducts(List<CartItem> products) {
        this.products = products;
    }
}

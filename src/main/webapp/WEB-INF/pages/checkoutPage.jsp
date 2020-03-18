<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Checkout">
    <div class="container">
        <div class="row">
            <div class="col s10">
                <h3>Order Checkout</h3>
                <div class="divider"></div>
                <div class="row">
                    <div id="cart-items-list" class="col s12">
                        <c:forEach var="cartItem" items="${cart.cartItems}">
                            <c:set var="product" value="${cartItem.product}" />
                            <div class="cart-item">
                                <div class="row">
                                    <div class="col s3">
                                        <img src="${product.imageUrl}" class="cart-item-img" />
                                    </div>
                                    <div class="col s4">
                                        <h1 class="headline"><c:out value="${product.description}" /></h1>
                                    </div>
                                    <div class="col s1">
                                        <h6>
                                            Quantity
                                        </h6>
                                        <h6>
                                            <c:out value="${cartItem.quantity}"/>
                                        </h6>
                                    </div>
                                    <div class="col s1">
                                        <h6>Price</h6>
                                        <h6>
                                            <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                                        </h6>
                                    </div>
                                </div>
                                <div class="divider"></div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="col s2">
                <tags:pricesSideBar
                        subtotalPrice="${subtotalPrice}"
                        deliveryCost="${deliveryCost}"
                        totalPrice="${totalPrice}">
                </tags:pricesSideBar>
            </div>
        </div>
        <div class="row">
            <div class="col s5">
                <c:set var="client" value="${order.client}" />
                <div>
                    <div class="error"></div>
                    <input name="firstName" type="text" placeholder="First name" value="${client.firstName}" />
                </div>
                <div>
                    <div class="error"></div>
                    <input name="lastName" type="text" placeholder="Last name" value="${client.lastName}" />
                </div>
                <div>
                    <div class="error"></div>
                    <input name="phoneNumber" type="text" placeholder="Phone number" value="${client.phoneNumber}" />
                </div>
                <div>
                    <div class="error"></div>
                    <input name="address" type="text" placeholder="Address" value="${client.address}" />
                </div>
                <div>
                    <div class="error"></div>
                    <input name="deliveryDate" type="date" placeholder="Delivery date" value="${order.deliveryDate}" />
                </div>
                <select name="paymentMethod">
                    <option value="cash">Cash</option>
                    <option value="credit_card">Credit card</option>
                </select>
                <button name="place-order" class="btn">Place order</button>
            </div>
        </div>
    </div>
</tags:master>

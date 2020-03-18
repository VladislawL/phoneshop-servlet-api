<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Overview">
    <div class="container">
        <div class="row">
            <div class="col s10">
                <h3>Order Overview</h3>
                <div class="divider"></div>
                <div class="row">
                    <div id="cart-items-list" class="col s12">
                        <c:forEach var="orderItem" items="${order.products}">
                            <c:set var="product" value="${orderItem.product}" />
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
                                            <c:out value="${orderItem.quantity}"/>
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
                <p>First Name: ${client.firstName}</p>
                <p>Last Name: ${client.lastName}</p>
                <p>Phone Number: ${client.phoneNumber}</p>
                <p>Address: ${client.address}</p>
                <p>Delivery Date: <fmt:formatDate value="${order.deliveryDate}" type="date" /></p>
            </div>
        </div>
    </div>
</tags:master>

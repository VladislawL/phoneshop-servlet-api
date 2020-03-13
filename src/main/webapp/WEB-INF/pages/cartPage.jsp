<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Shoping Cart">
    <div class="container">
        <div class="row">
            <div class="col s12">
                <p id="status"></p>
            </div>
        </div>
        <div class="row">
            <div class="col s10">
                <h3>Shopping Cart</h3>
                <div class="divider"></div>
            </div>
            <div class="col s2">
                <div class="total-price">
                    <div>
                        <h5>Total Price:</h5>
                        <h6 id="total-price"><c:out value="${totalPrice}" /></h6>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div id="cart-items-list" class="col s10">
                <c:forEach var="cartItem" items="${cart.cartItems}">
                    <c:set var="product" value="${cartItem.product}" />
                    <div class="cart-item">
                        <div class="row">
                            <div class="col s3">
                                <img src="${product.imageUrl}" class="cart-item-img" />
                            </div>
                            <div class="col s3">
                                <h1 class="headline"><c:out value="${product.description}" /></h1>
                            </div>
                            <div class="col s1">
                                <input class="input-quantity-field browser-default" name="quantity" type="number" value="<c:out value="${cartItem.quantity}" />" />
                                <input name="id" type="hidden" value="<c:out value="${product.id}" />" />
                                <div class="error"></div>
                            </div>
                            <div class="col s1">
                                <h6>
                                    <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                                </h6>
                            </div>
                            <div class="col s1">
                                <input type="button" value="Delete" class="btn delete-btn" />
                                <input name="id" type="hidden" value="<c:out value="${product.id}" />" />
                            </div>
                        </div>
                        <div class="divider"></div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="row">
            <div class="col s12">
                <p id="message" class="error"></p>
            </div>
        </div>
    </div>
</tags:master>
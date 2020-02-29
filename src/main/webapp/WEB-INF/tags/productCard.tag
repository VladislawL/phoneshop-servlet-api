<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ attribute name="id" required="true" %>
<%@ attribute name="description" required="true" %>
<%@ attribute name="imageUrl" required="true" %>
<%@ attribute name="price" required="true" %>
<%@ attribute name="currencySymbol" required="true" %>

<a href="products/${id}">
    <div class="col xl4 l4 m6 s12">
        <div class="card">
            <div class="card-content center">
                <h1 class="headline">${description}</h1>
                <div class="card-image">
                    <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${imageUrl}">
                </div>
                <p>
                    <fmt:formatNumber value="${price}" type="currency" currencySymbol="${currencySymbol}"/>
                </p>
            </div>
        </div>
    </div>
</a>
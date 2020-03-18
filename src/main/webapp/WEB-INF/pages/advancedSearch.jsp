<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Advanced Search">
    <div class="container">
        <form method="get">
            <p>Description</p>
            <input name="description" type="text" value="<c:out value="${param.description}" />">
            <p>Min Price</p>
            <div class="error">${errors["minPrice"]}</div>
            <input name="minPrice" type="number" value="<c:out value="${param.minPrice}" />">
            <p>Max price</p>
            <div class="error">${errors["maxPrice"]}</div>
            <input name="maxPrice" type="number" value="<c:out value="${param.maxPrice}" />">
            <p>Type</p>
            <select name="type">
                <option ${param.type eq "button" ? "selected" : ""} value="button">Button</option>
                <option ${param.type eq "smartphone" ? "selected" : ""} value="smartphone">Smart Phone</option>
                <option ${param.type eq "none" ? "selected" : ""} value="none">None</option>
            </select>
            <p>Operation System</p>
            <select name="operationSystem">
                <option ${param.operationSystem eq "ios" ? "selected" : ""} value="ios">IOS</option>
                <option ${param.operationSystem eq "android" ? "selected" : ""} value="android">Android</option>
                <option ${param.operationSystem eq "none" ? "selected" : ""} value="none">None</option>
            </select>
            <p>Color</p>
            <input name="color" type="text" value="<c:out value="${param.color}" />">
            <button type="submit">Search</button>
        </form>
        <div class="row">
            <div class="col s6">
                <c:forEach var="product" items="${products}">
                    <tags:productCard
                            id="${product.id}"
                            description="${product.description}"
                            imageUrl="${product.imageUrl}"
                            price="${product.price}"
                            currencySymbol="${product.currency.symbol}"
                    />
                </c:forEach>
            </div>
        </div>
    </div>
</tags:master>

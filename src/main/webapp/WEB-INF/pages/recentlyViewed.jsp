<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="recently-viewed-sidebar">
    <div>Recently viewed</div>
    <c:forEach var="product" items="${recentlyViewedProducts}">
        <div class="sidebar-item">
            <div><img src="${product.imageUrl}" /></div>
            <a href="${pageContext.servletContext.contextPath}/products/${product.id}">${product.description}</a>
            <div><fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/></div>
        </div>
    </c:forEach>
</div>

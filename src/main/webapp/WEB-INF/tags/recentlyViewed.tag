<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="recently-viewed-sidebar">
    <div>Recently viewed</div>
    <c:forEach var="product" items="${recentlyViewedProducts}">
        <div class="sidebar-item">
            <div><img class="sidebar-image" src="${product.imageUrl}" /></div>
            <a href="${pageContext.servletContext.contextPath}/products/${product.id}">${product.description}</a>
            <div><fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/></div>
        </div>
    </c:forEach>
</div>
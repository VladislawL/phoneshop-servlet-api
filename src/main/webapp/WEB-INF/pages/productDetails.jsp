<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product Detail">
    <c:if test="${not empty error}">
        <p class="error">There was an error</p>
    </c:if>
    <c:if test="${param.success}">
        <p class="success">Added to cart successfully</p>
    </c:if>
    <table>
        <thead>
            <td>
                Image
            </td>
            <td>
                Description
            </td>
            <td>
                Stock Level
            </td>
            <td>
                Price
            </td>
        </thead>
        <tbody>
            <tr>
                <td>
                    <img class="product-tile" src="${product.imageUrl}">
                </td>
                <td>
                    <c:out value="${product.description}"/>
                </td>
                <td>
                    <c:out value="${product.stock}"/>
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/priceHistory/${product.id}">
                        <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                    </a>
                </td>
            </tr>
        </tbody>
    </table>
    <form method="post" action="${product.id}">
        <input type="number" name="quantity" value="<c:out value="${not empty param.quantity ? param.quantity : 1}"/>">
        <button type="submit">Add</button>
    </form>
    <p class="error">
        ${error}
    </p>
</tags:master>

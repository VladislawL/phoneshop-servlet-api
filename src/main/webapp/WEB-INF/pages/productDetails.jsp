<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product Detail">
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
</tags:master>

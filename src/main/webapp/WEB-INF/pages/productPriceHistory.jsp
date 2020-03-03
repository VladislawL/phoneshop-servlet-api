<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product Detail">
    <div class="container">
        <br>
        <h1>Price History</h1>
        <h2>${product.description}</h2>
        <table class="borderless">
            <thead>
                <th>
                    Start Date
                </th>
                <th>
                    Price
                </th>
            </thead>
            <tbody>
                <c:forEach var="previousPrice" items="${product.previousPrices.entrySet()}">
                    <tr>
                        <td><fmt:formatDate value="${previousPrice.getKey()}"/></td>
                        <td><fmt:formatNumber value="${previousPrice.getValue()}" type="currency" currencySymbol="${product.currency.symbol}"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</tags:master>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product Detail">
    <div class="container">
        <div class="row">
            <div class="col s12">
                <p id="status"></p>
            </div>
        </div>
        <div class="row">
            <div class="col s8">
                <div class="card">
                    <table>
                        <tr>
                            <tr>
                                <td>Image</td>
                                <td>
                                    <img class="product-tile" src="${product.imageUrl}">
                                </td>
                            </tr>
                            <tr>
                                <td>Description</td>
                                <td>
                                    <c:out value="${product.description}"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Stock</td>
                                <td>
                                    <c:out value="${product.stock}"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Price</td>
                                <td>
                                    <a href="${pageContext.servletContext.contextPath}/products/priceHistory/${product.id}">
                                        <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                                    </a>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col s3">
                <input type="number" name="quantity" value="<c:out value="${not empty param.quantity ? param.quantity : 1}"/>">
            </div>
            <div class="col s2">
                <input type="submit" value="Add" class="btn">
            </div>
        </div>
        <div class="row">
            <div class="col s12">
                <p id="message" class="error"></p>
            </div>
        </div>
    </div>
</tags:master>

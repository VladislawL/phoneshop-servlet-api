<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
  <p>
    Welcome to Expert-Soft training!
  </p>
  <div class="container">
    <div class="row">
      <tags:filter/>
      <div class="col xl8 l8 m10">
        <tags:search/>
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
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>${pageTitle}</title>
  <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
  <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">

  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

  <script src="https://code.jquery.com/jquery-1.10.2.js"
          type="text/javascript"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
  <script src="${pageContext.servletContext.contextPath}/js/scripts.js" async></script>
</head>
<body class="product-list">

  <header class="navbar-fixed">
    <nav class="teal accent-3">
      <a href="${pageContext.servletContext.contextPath}" class="brand-logo left">
        <img src="${pageContext.servletContext.contextPath}/images/logo.svg" height="40"/>
        Phoneshop
      </a>
      <ul class="right">
        <li>
          <a href="${pageContext.servletContext.contextPath}/cart">
            <div class="cart left">
              <i class="medium material-icons navbar-element">shopping_cart</i>
            </div>
            <div class="cart-quantity left navbar-element">${cartQuantity}</div>
          </a>
        </li>
      </ul>
    </nav>
  </header>
  <main>
    <jsp:doBody/>
    <c:if test="${not empty recentlyViewedProducts}">
      <tags:recentlyViewed/>
    </c:if>
  </main>
</body>
</html>
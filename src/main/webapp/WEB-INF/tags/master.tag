<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<html>
<head>
  <title>${pageTitle}</title>
  <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
  <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">

  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

  <script src="https://code.jquery.com/jquery-1.10.2.js"
          type="text/javascript" async></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js" async></script>
  <script src="${pageContext.servletContext.contextPath}/js/scripts.js" async></script>
</head>
<body class="product-list">

  <header class="navbar-fixed">
    <nav class="teal accent-3">
      <div class="nav-wrapper">
        <a href="${pageContext.servletContext.contextPath}" class="brand-logo">
          <img src="${pageContext.servletContext.contextPath}/images/logo.svg" height="40"/>
          Phoneshop
        </a>
      </div>
    </nav>
  </header>
  <main>
    <jsp:doBody/>
    <tags:recentlyViewed/>
  </main>
</body>
</html>
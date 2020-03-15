<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Too Many Requests">
    <c:set var="recentlyViewedProducts" value="${null}" />
    <h3>You've done too many requests. Try again in ${pageContext.servletContext.getInitParameter("dosTimeOut")} seconds</h3>
</tags:master>
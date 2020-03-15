<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="subtotalPrice" required="true" %>
<%@ attribute name="deliveryCost" required="true" %>
<%@ attribute name="totalPrice" required="true" %>

<h5>Subtotal Price:</h5>
<h6 id="products-price"><c:out value="${subtotalPrice}" /></h6>
<h5>Delivery Price:</h5>
<h6 id="delivery-cost"><c:out value="${deliveryCost}" /></h6>
<h5>Total Price:</h5>
<h6 id="total-price"><c:out value="${totalPrice}" /></h6>
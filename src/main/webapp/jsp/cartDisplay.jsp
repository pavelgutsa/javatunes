<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head><title>Shopping Cart</title></head>
<body>
  <%@ include file="/jsp/header.jsp" %>
  <c:choose>
  <c:when test="${empty ShoppingCart.contents}">
    Your shopping cart is empty.
  </c:when>
  <c:when test="${empty requestScope.error}">
  <c:set var="total" value="0.0"/>
  Your shopping cart contains:
  <table border="1">
    <thead>
      <tr>
        <th>Name</th>
        <th>Artist</th>
        <th>Release Date</th>
        <th>List Price</th>
        <th><font color='green'>Your Price</font></th>
        <th>Quantity</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${ShoppingCart.contents}" var="item">
      <tr>
        <td><c:out value="${item.merchandiseItem.title}"/></td>
        <td><c:out value="${item.merchandiseItem.artist}"/></td>
        <td><fmt:formatDate value="${item.merchandiseItem.releaseDate}" type="date" dateStyle="long"/></td>
        <td><fmt:formatNumber value="${item.itemPrice.listPrice}" type="currency"/></td>
        <td><b><font color='green'><fmt:formatNumber value="${item.itemPrice.price}" type="currency"/></font></b></td>
        <td><c:out value="${item.itemQuantity}"/></td>
      </tr>
      <c:set var="total" value="${total + (item.itemPrice.price * item.itemQuantity)}"/>
      </c:forEach>
    </tbody>
  </table>
  The total cost of your order is: <b><font color='green'><fmt:formatNumber value="${total}" type="currency"/></font></b>      
  </c:when>
  <c:otherwise>
  	<c:out value="${requestScope.error}"/>
  </c:otherwise>
  </c:choose>
  <HR/>
  <form method='GET' action='/cart'>
    <input type='submit' name='checkout' value='Checkout'/>
  </form>
</body>
</html>

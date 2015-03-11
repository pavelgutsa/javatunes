<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<HTML>
<HEAD><TITLE>Search</TITLE></HEAD>
<BODY>
  <%@ include file="/jsp/header.jsp" %>
  Search results for keyword <B><c:out value="${param.keyword}"/></B>:
  <P>
  <c:choose>
  <c:when test="${empty requestScope.results}">
    Sorry, your search produced no results.
  </c:when>
  <c:otherwise>
  The number of items found is: <B><c:out value="${requestScope.size}"/></B>
  <table border="1">
    <thead>
      <tr>
        <th>Name</th>
        <th>Artist</th>
        <th>Release Date</th>
        <th>Available</th>
        <th>List Price</th>
        <th><font color='green'>Your Price</font></th>
        <th><font color='blue'>Buy Now</font></th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${requestScope.results}" var="catalogItem">
      <tr>
        <td><c:out value="${catalogItem.inventoryItem.merchandiseItem.title}"/></td>
        <td><c:out value="${catalogItem.inventoryItem.merchandiseItem.artist}"/></td>
        <td><fmt:formatDate value="${catalogItem.inventoryItem.merchandiseItem.releaseDate}" type="date" dateStyle="medium"/></td>
        <td><c:out value="${catalogItem.inventoryItem.itemQuantity}"/></td>
        <td><fmt:formatNumber value="${catalogItem.itemPrice.listPrice}" type="currency"/></td>
        <td><b><font color='green'><fmt:formatNumber value="${catalogItem.itemPrice.price}" type="currency"/></font></b></td>
        <td><a href='<c:url value="/cart"><c:param name="itemID" value="${catalogItem.inventoryItem.merchandiseItem.id}"/></c:url>'>Add to cart</a></td>
      </tr>
      </c:forEach>
    </tbody>
  </table>
  </c:otherwise>
  </c:choose>
</BODY>
</HTML>

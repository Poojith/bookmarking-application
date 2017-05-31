<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div
	style="font-size: medium; margin-left: 35%; margin-top: 30px; color: red;">
	<c:if test="${not empty errors}">
		<c:forEach var="error" items="${errors}">
			<p>${error}</p>
		</c:forEach>
		</p>
	</c:if>
</div>
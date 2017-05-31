<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="template.jsp" />

<jsp:include page="error-list.jsp" />


<table style="margin-left: 30%;">
	<tr>
		<td style="background-color: #31e6ed; padding: 5px;">List of
			favorites for ${userName}</td>
	</tr>
	<tr></tr>
	<c:forEach var="favorite" items="${favoritesList}">
		<form id="countForm${favorite.favoriteId}" method="POST"
			action="update-click.do">
			<tr style="margin-bottom: 2px">
				<td><input type="hidden" name="favoriteID"
					value="${favorite.favoriteId}" /> <a href="http://${favorite.url}"
					target="_blank"
					onclick="document.getElementById('countForm${favorite.favoriteId}').submit()">
						<b> ${favorite.url}</b>
				</a></td>
			</tr>
			<tr>
				<td>${favorite.comment}</td>
			</tr>
			<tr>
				<td>${favorite.clickCount} click(s)</td>
			</tr>
		</form>
	</c:forEach>
</table>

</body>
</html>
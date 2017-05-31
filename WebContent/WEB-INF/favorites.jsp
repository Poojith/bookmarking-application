<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="template.jsp" />

<jsp:include page="error-list.jsp" />

<form id="favoritesForm" action="update-favorite.do" method="POST">
	<table style="margin-left: 35%; margin-top: 20px; text-align: center">
		<tr style="margin-bottom: 2px;">
			<td><span> URL </span></td>
			<td><input type="text" name="url" id="urlID"
				form="favoritesForm" placeholder="www.example.com"></td>
		</tr>
		<tr style="margin-bottom: 2px;">
			<td><span style="padding: 5px;"> Comment </span></td>
			<td><input type="text" name="comment" form="favoritesForm"
				id="commentID" placeholder="Your comment"></td>
		</tr>

		<tr style="margin-top: 5px; margin-left: 10px;">
			<td></td>
			<td style="text-align: center; padding: 5px;"><input
				type="submit" name="favoriteButton" value="Add favorite"
				form="favoritesForm"
				style="border: 0.2px solid grey; background-color: #44ff4e; width: 100px; height: 30px">
	</table>
</form>


<table style="margin-left: 30%;">
	<c:forEach var="favorite" items="${favoritesList}">
		<form id="countForm${favorite.favoriteId}" method="POST"
			action="update-click.do">
			<tr style="margin-bottom: 2px">
				<td><input type="hidden" name="favoriteID"
					value="${favorite.favoriteId}" /> <a href="http://${favorite.url}"
					target="_blank"
					onclick="document.getElementById('countForm${favorite.favoriteId}').submit()">
						<b> ${favorite.url} </b>
				</a></td>
			</tr>

			<tr>
				<td>${favorite.comment}</td>
			</tr>
			<tr>
				<td>${favorite.clickCount}click(s)</td>
			</tr>
		</form>
		<tr>

			<td>

				<form id="deleteForm${favorite.favoriteId}" method="POST"
					action="remove.do">
					<input type="hidden" name="favoriteID"
						value="${favorite.favoriteId}" /> <span><input
						name="delete" style="background-color: #ce4658; height: 25px"
						type="submit" value="Delete favorite"></span>
				</form>
			</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>
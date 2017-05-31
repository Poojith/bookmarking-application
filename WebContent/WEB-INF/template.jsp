<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="pragma" content="no-cache">
<title>Web Favorites</title>
<style>
h4 {
	font: normal 27px/1.5 'Open Sans', sans-serif;
	color: #DDDDDD;
	margin: 1px 0;
	background-color: #07343d;
	text-align: center;
	margin-bottom: 10px;
}

.navBar ul {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 20%;
	background-color: #d9e7fc;
	position: fixed;
	height: 85%;
	overflow: auto;
}

.navBar li a {
	display: block;
	background-color: #3785f2;
	text-decoration: none;
	text-align: center;
	color: white;
	margin-bottom: 2px;
}

.navBar li a:hover {
	background-color: #555;
	color: white;
}
</style>
</head>

<body>
	<h4>
		<b>WebFavorites</b>
	</h4>
	<div class="navBar">

		<c:if test="${empty user}">
			<ul>
				<li><a href="register.do">Register</a></li>
				<li><a href="login.do">Login</a></li>
				<br>
				<li style="text-align: center"><b>List of users</b></li>
		</c:if>

		<c:if test="${not empty user}">
			<ul>
				<li style="text-align: center"><b>${user.firstName}
						${user.lastName}</b></li>
				<li><a href="manage.do">Manage your favorites</a></li>
				<li><a href="change-password.do">Change password</a></li>
				<li><a href="logout.do">Logout</a></li>
				<br>
				<li style="text-align: center"><b>List of users</b></li>
		</c:if>


		<c:forEach var="user" items="${userList}">
			<li><a href="list.do?userID=${user.userId}">
					${user.firstName} ${user.lastName} </a></li>
		</c:forEach>
		</ul>

	</div>
<jsp:include page="template.jsp" />

<jsp:include page="error-list.jsp" />

<p>
<form method="post" action="login.do">

	<table style="margin-left: 35%; margin-top: 40px;">
		<tr>
			<td colspan="2"><div
					style="background-color: #27a4e8; text-align: center;">
					<h3 style="color: #DDDDDD">Login</h3>
				</div></td>
		</tr>
		<tr>
			<td>E-mail address:</td>
			<td><input type="text" name="email" value=""
				placeholder="example@domain.com" /></td>
		</tr>
		<tr>
			<td>Password:</td>
			<td><input type="password" name="password" value=""
				placeholder="Password" /></td>
		</tr>
		<tr>
			<td></td>
			<td colspan="2" align="center"><input type="submit"
				name="action" value="Login" style="background-color: #44ff4e; width: 100px; height: 30px" /></td>
		</tr>
	</table>
</form>
</p>

</body>
</html>
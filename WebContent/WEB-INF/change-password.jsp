<jsp:include page="template.jsp" />

<jsp:include page="error-list.jsp" />

<p style="font-size: medium; margin-left: 35%; margin-top: 10px;">
	Enter your new password</p>

<p>
<form method="POST" action="change-password.do">
	<table style="margin-left: 35%; margin-top: 10px;">
		<tr>
			<td>New Password:</td>
			<td><input type="password" name="newPassword"
				placeholder="New password" value="" /></td>
		</tr>
		<tr>
			<td>Confirm New Password:</td>
			<td><input type="password" name="confirmPassword"
				placeholder="Confirm password" value="" /></td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" name="action" style="background-color: #42dcf4; height: 25px" value="Change Password" />
			</td>
		</tr>
	</table>
</form>
</p>

</body>
</html>
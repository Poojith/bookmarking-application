<jsp:include page="template.jsp" />

<p style="font-size:medium; margin-left: 35%;">
    To register, enter the following information. (All fields required.)
</p>

<jsp:include page="error-list.jsp" />

<p>
	<form method="post">
		<input type="hidden" name="redirect" value=""/>
		<table style="margin-left:35%; margin-top: 20px;">
      <tr> <td colspan="2"><div style="background-color: #27a4e8; text-align: center;"><h3 style="color: #DDDDDD"> Registration</h3></div></td> </tr>
      <tr>
				<td>E-mail address: </td>
				<td><input type="text" name="email" value="" placeholder="example@domain.com"/></td>
			</tr>
			<tr>
				<td>First Name: </td>
				<td><input type="text" name="firstName" value="" placeholder="First name"/></td>
			</tr>
			<tr>
				<td>Last Name: </td>
				<td><input type="text" name="lastName" value="" placeholder="Last name"/></td>
			</tr>

			<tr>
				<td>Password: </td>
				<td><input type="password" name="password" value="" placeholder="Password"/></td>
			</tr>
			<tr>
        <td></td>
				<td align="center">
					<input type="submit" style="background-color: #44ff4e; width: 100px; height: 30px;" name="registerButton" value="Register"/>
				</td>
			</tr>
		</table>
	</form>
</p>

  </body>
</html>

package formbeans;
/**
 * Homework 4 solution for favorite URLs/websites.
 * @author Poojith Kumar Jain (poojithj@andrew.cmu.edu)
 * Date: 07 December, 2016
 * Course: 08-672 (J2EE Web Application Development)
 */

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class LoginForm {
	private String emailId;
	private String password;
	private String button;

	public LoginForm(HttpServletRequest request) {

		String emailInput = request.getParameter("email");
		password = request.getParameter("password");

		if (emailInput != null)
			emailId = emailInput.trim();

		button = request.getParameter("action");
	}

	public String getEmailId() {
		return emailId;
	}

	public String getPassword() {
		return password;
	}

	public String getButton() {
		return button;
	}

	public boolean isPresent() {
		return button != null;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (emailId == null || emailId.length() == 0)
			errors.add("Email ID is required.");
		if (password == null || password.length() == 0)
			errors.add("Password is required.");
		if (button == null)
			errors.add("Button is required.");

		if (errors.size() > 0)
			return errors;

		if (!button.equals("Login"))
			errors.add("Invalid button.");
		String regex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(emailId);
		if (!match.matches())
			errors.add("Please check the format of the e-mail ID.");

		return errors;

	}
}

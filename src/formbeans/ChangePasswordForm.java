/**
 * Homework 4 solution for favorite URLs/websites.
 * @author Poojith Kumar Jain (poojithj@andrew.cmu.edu)
 * Date: 07 December, 2016
 * Course: 08-672 (J2EE Web Application Development)
 */

package formbeans;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class ChangePasswordForm {
	private String newPassword;
	private String confirmPassword;
	private String passwordButton;

	public ChangePasswordForm(HttpServletRequest request) {
		String password = request.getParameter("newPassword");
		String confirm = request.getParameter("confirmPassword");

		if (password != null) {
			newPassword = password.trim();
		}
		if (confirm != null) {
			confirmPassword = confirm.trim();
		}

		passwordButton = request.getParameter("action");
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getPasswordButton() {
		return passwordButton;
	}

	public boolean isPresent() {
		return passwordButton != null;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (newPassword == null || newPassword.length() == 0) {
			errors.add("New password cannot be empty");
		}
		if (confirmPassword == null || confirmPassword.length() == 0) {
			errors.add("Password cannot be empty. Please confirm your password. ");
		}

		if (errors.size() > 0) {
			return errors;
		}

		if (!newPassword.equals(confirmPassword)) {
			errors.add("The two passwords do not match. ");
		}

		return errors;
	}
}

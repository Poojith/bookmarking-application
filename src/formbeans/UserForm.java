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

public class UserForm {
	private String userID;
	private String button;

	public UserForm(HttpServletRequest request) {
		String userId = null;

		if (request.getParameter("userID") != null)
			userId = request.getParameter("userID");
		else
			userId = (String) request.getSession(false).getAttribute("userID");

		if (userId != null) {
			userID = userId.trim();
		}

		button = request.getParameter("delete");
	}

	public String getUserID() {
		return userID;

	}

	public String getButton() {
		return button;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public void setButton(String button) {
		this.button = button;
	}

	public boolean isPresent() {
		return userID != null;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (userID == null || userID.length() == 0)
			errors.add("User ID is required to display the list. Please use the navigation bar to choose the user.");

		return errors;
	}
}

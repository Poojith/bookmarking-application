package formbeans;
/**
 * Homework 4 solution for favorite URLs/websites.
 * @author Poojith Kumar Jain (poojithj@andrew.cmu.edu)
 * Date: 07 December, 2016
 * Course: 08-672 (J2EE Web Application Development)
 */

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class FavoriteIdForm {
	private String favoriteId;

	public FavoriteIdForm(HttpServletRequest request) {
		String favoriteIdInput = request.getParameter("favoriteID");
		if (favoriteIdInput != null)
			favoriteId = favoriteIdInput.trim();
	}

	public String getFavoriteId() {
		return favoriteId;
	}

	public int getFavoriteIdAsInt() {
		return Integer.parseInt(favoriteId);
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (favoriteId == null || favoriteId.length() == 0) {
			errors.add("Error in handling favorite ID.");
		}

		try {
			Integer.parseInt(favoriteId);
		} catch (NumberFormatException e) {
			errors.add("Error in ID conversion from String");
		}
		return errors;
	}

}

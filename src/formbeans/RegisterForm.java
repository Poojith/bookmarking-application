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

public class RegisterForm {
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private String button;

	public RegisterForm(HttpServletRequest request) {
		String emailInput = request.getParameter("email");
		String firstNameInput = request.getParameter("firstName");
		String lastNameInput = request.getParameter("lastName");
		password = request.getParameter("password");
		
		String emailTest = null;
		String fNameTest = null;
		String lNameTest = null;

		if (emailInput != null) {
			emailTest = fixBadChars(emailInput);
			email = emailTest.trim();
		}	
		if (firstNameInput != null) {
			fNameTest = fixBadChars(firstNameInput);
			firstName = fNameTest.trim();
		}
			
		if (lastNameInput != null) {
			lNameTest = fixBadChars(lastNameInput);
			lastName = lNameTest.trim();
		}
		
		button = request.getParameter("registerButton");
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
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

		if (email == null || email.length() == 0)
			errors.add("Email ID is required.");

		/**
		 * Validates the format of e-mail ID.
		 */
		String regex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(email);
		if (!match.matches())
			errors.add("Please check the format of the e-mail ID.");

		if (firstName == null || firstName.length() == 0)
			errors.add("First name is required.");
		if (lastName == null || lastName.length() == 0)
			errors.add("Last name is required.");
		if (password == null || password.length() == 0)
			errors.add("Password is required.");

		if (errors.size() > 0)
			return errors;

		if (!button.equals("Register"))
			errors.add("Invalid button.");

		return errors;
	}
	
	private String fixBadChars(String s) {
        if (s == null || s.length() == 0)
            return s;

        Pattern p = Pattern.compile("[<>\"&]");
        Matcher m = p.matcher(s);
        StringBuffer b = null;
        while (m.find()) {
            if (b == null)
                b = new StringBuffer();
            switch (s.charAt(m.start())) {
            case '<':
                m.appendReplacement(b, "&lt;");
                break;
            case '>':
                m.appendReplacement(b, "&gt;");
                break;
            case '&':
                m.appendReplacement(b, "&amp;");
                break;
            case '"':
                m.appendReplacement(b, "&quot;");
                break;
            default:
                m.appendReplacement(b, "&#" + ((int) s.charAt(m.start())) + ';');
            }
        }

        if (b == null)
            return s;
        m.appendTail(b);
        return b.toString();
    }
}

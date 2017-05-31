/**
 * Homework 4 solution for favorite URLs/websites.
 * @author Poojith Kumar Jain (poojithj@andrew.cmu.edu)
 * Date: 07 December, 2016
 * Course: 08-672 (J2EE Web Application Development)
 */

package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import databeans.UserBean;
import formbeans.ChangePasswordForm;
import model.Model;
import model.UserDAO;

public class ChangePasswordAction extends Action {
	private UserDAO userDAO;

	public ChangePasswordAction(Model model) {
		userDAO = model.getUserDAO();
	}

	@Override
	public String getName() {
		return "change-password.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			request.setAttribute("userList", userDAO.getUsers());
			
			ChangePasswordForm form = new ChangePasswordForm(request);		
			
			if (!form.isPresent()) {
				return "change-password.jsp";
			}
			
			errors.addAll(form.getValidationErrors());
			if (errors.size() > 0) {
				return "change-password.jsp";
			}

			UserBean user = (UserBean) request.getSession(false).getAttribute("user");
			if (user == null) {
				errors.add("Error loading the user's details for change of password");
				return "change-password.jsp";
			}

			UserBean updatedUser = userDAO.setPassword(user.getEmail(), form.getNewPassword());

			HttpSession session = request.getSession();
			session.setAttribute("user", updatedUser);

			request.setAttribute("message", "You have successfully changed your password.");
			return "success.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (Exception e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}
}

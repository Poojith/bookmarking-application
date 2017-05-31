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
import formbeans.LoginForm;
import model.Model;
import model.UserDAO;

public class LoginAction extends Action {
	private UserDAO userDAO;

	public LoginAction(Model model) {
		userDAO = model.getUserDAO();
	}

	@Override
	public String getName() {
		return "login.do";
	}

	@Override
	public String perform(HttpServletRequest request) {

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			request.setAttribute("userList", userDAO.getUsers());

			LoginForm form = new LoginForm(request);
			request.setAttribute("form", form);

			if (!form.isPresent()) {
				return "login.jsp";
			}

			errors.addAll(form.getValidationErrors());
			if (errors.size() > 0) {
				return "login.jsp";
			}

			UserBean user = userDAO.read(form.getEmailId());
			if (user == null) {
				errors.add("No such user.");
				return "login.jsp";
			}

			if (!user.checkPassword(form.getPassword())) {
				errors.add("Incorrect password");
				return "login.jsp";
			}

			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			return "manage.do";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (Exception e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}
}

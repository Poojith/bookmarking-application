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
import formbeans.RegisterForm;
import model.Model;
import model.UserDAO;

public class RegisterAction extends Action {
	private UserDAO userDAO;

	public RegisterAction(Model model) {
		userDAO = model.getUserDAO();
	}

	@Override
	public String getName() {
		return "register.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			request.setAttribute("userList", userDAO.getUsers());

			RegisterForm form = new RegisterForm(request);
			
			if (!form.isPresent()) {
				return "register.jsp";
			}
			
			errors.addAll(form.getValidationErrors());
			if (errors.size() > 0) {
				return "register.jsp";
			}

			if (userDAO.isEmailPresent(form.getEmail())) {
				errors.add("Sorry, the e-mail ID is already registered.");
				return "register.jsp";
			}

			UserBean user = new UserBean();
			user.setEmail(form.getEmail());
			user.setFirstName(form.getFirstName());
			user.setLastName(form.getLastName());
			user.encodePassword(form.getPassword());
			userDAO.create(user);

			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			return "manage.do";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "register.jsp";
		} catch (Exception e) {
			errors.add(e.getMessage());
			return "register.jsp";
		}
	}
}

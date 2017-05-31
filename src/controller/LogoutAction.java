/**
 * Homework 4 solution for favorite URLs/websites.
 * @author Poojith Kumar Jain (poojithj@andrew.cmu.edu)
 * Date: 07 December, 2016
 * Course: 08-672 (J2EE Web Application Development)
 */

package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import model.Model;
import model.UserDAO;

public class LogoutAction extends Action {
	private UserDAO userDAO;

	public LogoutAction(Model model) {
		userDAO = model.getUserDAO();
	}

	@Override
	public String getName() {
		return "logout.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		session.setAttribute("user", null);
		try {
			request.setAttribute("userList", userDAO.getUsers());
		} catch (RollbackException e) {
			return "error.jsp";
		} catch (Exception e) {
			return "error.jsp";
		}

		request.setAttribute("message", "You have successfully logged out.");
		return "success.jsp";
	}
}

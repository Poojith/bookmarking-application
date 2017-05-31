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

import databeans.FavoriteBean;
import databeans.UserBean;
import formbeans.UserForm;
import model.FavoriteDAO;
import model.Model;
import model.UserDAO;

public class ListAction extends Action {
	private UserDAO userDAO;
	private FavoriteDAO favoriteDAO;

	public ListAction(Model model) {
		userDAO = model.getUserDAO();
		favoriteDAO = model.getFavoriteDAO();
	}

	@Override
	public String getName() {
		return "list.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			request.setAttribute("userList", userDAO.getUsers());
			UserForm form = new UserForm(request);

			errors.addAll(form.getValidationErrors());
			if (errors.size() > 0) {
				return "error.jsp";
			}

			String userID = form.getUserID();
			String emailID = userDAO.getEmailId(userID);
			UserBean user = userDAO.read(emailID);

			if (user == null) {
				errors.add("Invalid user ID: " + form.getUserID());
				return "error.jsp";
			}

			FavoriteBean[] favoriteList = favoriteDAO.getUserFavorites(user.getUserId());
			request.setAttribute("favoritesList", favoriteList);
			HttpSession session = request.getSession();
			/*
			 * Set userID in the session for UpdateClickAction to access the ID.
			 */
			session.setAttribute("userID", form.getUserID());
			String userName = user.getFirstName() + " " + user.getLastName();
			session.setAttribute("userName", userName);
			return "list.jsp";

		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (Exception e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}
}

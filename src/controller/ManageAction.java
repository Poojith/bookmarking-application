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

import org.genericdao.RollbackException;

import databeans.FavoriteBean;
import databeans.UserBean;
import model.FavoriteDAO;
import model.Model;
import model.UserDAO;

public class ManageAction extends Action {
	private UserDAO userDAO;
	private FavoriteDAO favoriteDAO;

	public ManageAction(Model model) {
		userDAO = model.getUserDAO();
		favoriteDAO = model.getFavoriteDAO();
	}

	@Override
	public String getName() {
		return "manage.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			UserBean[] users = userDAO.getUsers();
			request.setAttribute("userList", users);

			UserBean user = (UserBean) request.getSession(false).getAttribute("user");
			FavoriteBean[] favorites = favoriteDAO.getUserFavorites(user.getUserId());
			request.setAttribute("favoritesList", favorites);
			request.setAttribute("userID", user.getUserId());

			return "favorites.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "register.jsp";
		} catch (Exception e) {
			errors.add(e.getMessage());
			return "register.jsp";
		}
	}
}

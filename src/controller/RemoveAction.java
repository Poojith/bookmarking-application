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
import formbeans.FavoriteIdForm;
import model.FavoriteDAO;
import model.Model;
import model.UserDAO;

public class RemoveAction extends Action {
	private UserDAO userDAO;
	private FavoriteDAO favoriteDAO;

	public RemoveAction(Model model) {
		userDAO = model.getUserDAO();
		favoriteDAO = model.getFavoriteDAO();
	}

	@Override
	public String getName() {
		return "remove.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			request.setAttribute("userList", userDAO.getUsers());

			FavoriteIdForm idForm = new FavoriteIdForm(request);
			UserBean user = (UserBean) request.getSession().getAttribute("user");
			int id = idForm.getFavoriteIdAsInt();
			favoriteDAO.deleteFavorite(id, user.getUserId());

			FavoriteBean[] favorites = favoriteDAO.getUserFavorites(user.getUserId());
			request.setAttribute("favoritesList", favorites);

			return "favorites.jsp";
		}

		catch (RollbackException e) {
			errors.add(e.getMessage());
			return "login.jsp";
		} catch (Exception e) {
			errors.add(e.getMessage());
			return "login.jsp";
		}
	}
}

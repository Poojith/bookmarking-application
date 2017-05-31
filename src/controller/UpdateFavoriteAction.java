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
import formbeans.CommentForm;
import formbeans.UrlForm;
import model.FavoriteDAO;
import model.Model;
import model.UserDAO;

public class UpdateFavoriteAction extends Action {
	private UserDAO userDAO;
	private FavoriteDAO favoriteDAO;

	public UpdateFavoriteAction(Model model) {
		userDAO = model.getUserDAO();
		favoriteDAO = model.getFavoriteDAO();
	}

	@Override
	public String getName() {
		return "update-favorite.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			request.setAttribute("userList", userDAO.getUsers());

			UserBean user = (UserBean) request.getSession(false).getAttribute("user");
			if (user == null) {
				errors.add("Error in logging in. Please reload the page and try again.");
				return "error.jsp";
			}

			FavoriteBean[] favorites = favoriteDAO.getUserFavorites(user.getUserId());
			request.setAttribute("favoritesList", favorites);

			UrlForm urlForm = new UrlForm(request);
			CommentForm commentForm = new CommentForm(request);

			errors.addAll(urlForm.getValidationErrors());
			if (errors.size() > 0) {
				return "favorites.jsp";
			}

			errors.addAll(commentForm.getValidationErrors());
			if (errors.size() > 0) {
				return "favorites.jsp";
			}

			FavoriteBean favorite = new FavoriteBean();
			favorite.setUserId(user.getUserId());
			favorite.setUrl(urlForm.getUrl());
			favorite.setComment(commentForm.getComment());
			favoriteDAO.create(favorite);

			FavoriteBean[] updatedFavorites = favoriteDAO.getUserFavorites(user.getUserId());
			request.setAttribute("favoritesList", updatedFavorites);

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

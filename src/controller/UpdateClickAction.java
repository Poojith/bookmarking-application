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
import formbeans.FavoriteIdForm;
import model.FavoriteDAO;
import model.Model;
import model.UserDAO;

public class UpdateClickAction extends Action {
	private UserDAO userDAO;
	private FavoriteDAO favoriteDAO;

	public UpdateClickAction(Model model) {
		userDAO = model.getUserDAO();
		favoriteDAO = model.getFavoriteDAO();
	}

	@Override
	public String getName() {
		return "update-click.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			UserBean[] users = userDAO.getUsers();
			request.setAttribute("userList", users);

			FavoriteIdForm form = new FavoriteIdForm(request);
			errors.addAll(form.getValidationErrors());

			if (errors.size() > 0) {
				return "error.jsp";
			}

			/*
			 * Retrieve the favorite to be updated by getFavoriteIdAsInt (from
			 * the form, FavoriteIdForm)
			 */
			if (form != null) {
				FavoriteBean favorite = favoriteDAO.getFavoriteBean(form.getFavoriteIdAsInt());
				favoriteDAO.updateClick(favorite);
			}

			/*
			 * Get user and userID from the session. Set userID to current
			 * page's user. When the request comes from Manage.do, the user is
			 * logged in, but the user's userID is not originally passed as a
			 * request parameter or as a session attribute. Hence, when the
			 * userID is null, set it to current page's user.
			 */
			UserBean user = (UserBean) request.getSession(false).getAttribute("user");
			String userID = (String) request.getSession(true).getAttribute("userID");
			if (userID == null) {
				userID = String.valueOf(user.getUserId());
			}

			/*
			 * Retrieve list of favorites for the logged in user. Here, the
			 * userID is not null. It is passed from the previous page (list) as
			 * a session attribute.
			 */
			if (user != null) {
				FavoriteBean[] favorites = favoriteDAO.getUserFavorites(user.getUserId());
				request.setAttribute("favoritesList", favorites);
				String userName = user.getFirstName() + " " + user.getLastName();
				request.setAttribute("userName", userName);
			}
			/*
			 * User has not logged in, but is viewing the page as a guest user.
			 * Hence, user is null. Parse the userID from the previously passed
			 * session value and retreive favorite details.
			 */
			else {
				int intUserID = Integer.parseInt(userID);
				FavoriteBean[] favorites = favoriteDAO.getUserFavorites(intUserID);
				request.setAttribute("favoritesList", favorites);
			}

			HttpSession session = request.getSession();
			session.setAttribute("userID", userID);

			/*
			 * ListAction has to be processed again to get details of userID
			 * (from UserForm).
			 */
			return "list.do";

		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "login.jsp";
		} catch (Exception e) {
			errors.add(e.getMessage());
			return "login.jsp";
		}
	}
}

/**
 * Homework 4 solution for favorite URLs/websites.
 * @author Poojith Kumar Jain (poojithj@andrew.cmu.edu)
 * Date: 07 December, 2016
 * Course: 08-672 (J2EE Web Application Development)
 */

package model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.RollbackException;

import databeans.FavoriteBean;
import databeans.UserBean;

public class Model {

	private UserDAO userDAO;
	private FavoriteDAO favoriteDAO;

	public Model(ServletConfig config) throws ServletException {
		try {
			String jdbcDriverName = config.getInitParameter("jdbcDriver");
			String jdbcURL = config.getInitParameter("jdbcURL");

			ConnectionPool cp = new ConnectionPool(jdbcDriverName, jdbcURL);
			userDAO = new UserDAO(cp, "poojithj_user");
			favoriteDAO = new FavoriteDAO(cp, "poojithj_favorite");
			if (userDAO.getUsers().length < 3)
				initializeUsers();
		} catch (DAOException e) {
			throw new ServletException(e);
		} catch (RollbackException e) {
			e.printStackTrace();
		}
	}

	private void initializeUsers() throws RollbackException {
		initializeUserOne();
		initializeUserTwo();
		initializeUserThree();
	}

	private void initializeUserOne() throws RollbackException {
		UserBean user = new UserBean();
		user.setEmail("johndoe@cmu.edu");
		user.setFirstName("John");
		user.setLastName("Doe");
		user.encodePassword("1");
		userDAO.create(user);

		FavoriteBean favoriteOne = new FavoriteBean();
		favoriteOne.setUrl("www.cmu.edu");
		favoriteOne.setComment("Carnegie Mellon University");
		favoriteOne.setClickCount(0);
		favoriteOne.setUserId(user.getUserId());

		FavoriteBean favoriteTwo = new FavoriteBean();
		favoriteTwo.setUrl("www.cs.cmu.edu");
		favoriteTwo.setComment("School of Computer Science, CMU");
		favoriteTwo.setClickCount(0);
		favoriteTwo.setUserId(user.getUserId());

		FavoriteBean favoriteThree = new FavoriteBean();
		favoriteThree.setUrl("www.library.cmu.edu");
		favoriteThree.setComment("CMU library");
		favoriteThree.setClickCount(0);
		favoriteThree.setUserId(user.getUserId());

		FavoriteBean favoriteFour = new FavoriteBean();
		favoriteFour.setUrl("www.isri.cmu.edu/");
		favoriteFour.setComment("Institute for Software Research, CMU");
		favoriteFour.setClickCount(0);
		favoriteFour.setUserId(user.getUserId());

		favoriteDAO.create(favoriteOne);
		favoriteDAO.create(favoriteTwo);
		favoriteDAO.create(favoriteThree);
		favoriteDAO.create(favoriteFour);
	}

	private void initializeUserTwo() throws RollbackException {
		UserBean user = new UserBean();
		user.setEmail("janedoe@cmu.edu");
		user.setFirstName("Jane");
		user.setLastName("Doe");
		user.encodePassword("1");
		userDAO.create(user);

		FavoriteBean favoriteOne = new FavoriteBean();
		favoriteOne.setUrl("home.google.com");
		favoriteOne.setComment("Google Home");
		favoriteOne.setClickCount(0);
		favoriteOne.setUserId(user.getUserId());

		FavoriteBean favoriteTwo = new FavoriteBean();
		favoriteTwo.setUrl("fonts.google.com");
		favoriteTwo.setComment("Google Fonts");
		favoriteTwo.setClickCount(0);
		favoriteTwo.setUserId(user.getUserId());

		FavoriteBean favoriteThree = new FavoriteBean();
		favoriteThree.setUrl("www.google.com/voice");
		favoriteThree.setComment("Google Voice");
		favoriteThree.setClickCount(0);
		favoriteThree.setUserId(user.getUserId());

		FavoriteBean favoriteFour = new FavoriteBean();
		favoriteFour.setUrl("www.keep.google.com");
		favoriteFour.setComment("Google Keep");
		favoriteFour.setClickCount(0);
		favoriteFour.setUserId(user.getUserId());

		favoriteDAO.create(favoriteOne);
		favoriteDAO.create(favoriteTwo);
		favoriteDAO.create(favoriteThree);
		favoriteDAO.create(favoriteFour);
	}

	private void initializeUserThree() throws RollbackException {
		UserBean user = new UserBean();
		user.setEmail("appleseed@cmu.edu");
		user.setFirstName("Johnny");
		user.setLastName("Appleseed");
		user.encodePassword("1");
		userDAO.create(user);

		FavoriteBean favoriteOne = new FavoriteBean();
		favoriteOne.setUrl("www.techcrunch.com");
		favoriteOne.setComment("TechCrunch");
		favoriteOne.setClickCount(0);
		favoriteOne.setUserId(user.getUserId());

		FavoriteBean favoriteTwo = new FavoriteBean();
		favoriteTwo.setUrl("www.wired.com");
		favoriteTwo.setComment("Wired");
		favoriteTwo.setClickCount(0);
		favoriteTwo.setUserId(user.getUserId());

		FavoriteBean favoriteThree = new FavoriteBean();
		favoriteThree.setUrl("www.mashable.com");
		favoriteThree.setComment("Mashable");
		favoriteThree.setClickCount(0);
		favoriteThree.setUserId(user.getUserId());

		FavoriteBean favoriteFour = new FavoriteBean();
		favoriteFour.setUrl("www.thenextweb.com");
		favoriteFour.setComment("TNW - TheNextWeb");
		favoriteFour.setClickCount(0);
		favoriteFour.setUserId(user.getUserId());

		favoriteDAO.create(favoriteOne);
		favoriteDAO.create(favoriteTwo);
		favoriteDAO.create(favoriteThree);
		favoriteDAO.create(favoriteFour);
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public FavoriteDAO getFavoriteDAO() {
		return favoriteDAO;
	}
}

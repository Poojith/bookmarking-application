/**
 * Homework 4 solution for favorite URLs/websites.
 * @author Poojith Kumar Jain (poojithj@andrew.cmu.edu)
 * Date: 07 December, 2016
 * Course: 08-672 (J2EE Web Application Development)
 */

package model;

import java.util.Arrays;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.UserBean;

public class UserDAO extends GenericDAO<UserBean> {
	public UserDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(UserBean.class, tableName, cp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.genericdao.GenericDAO#create(java.lang.Object)
	 */
	public void create(UserBean user) throws RollbackException {
		try {
			Transaction.begin();
			super.create(user);
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

	/**
	 * @return Sorted list of users in the website
	 * @throws RollbackException
	 */
	public UserBean[] getUsers() throws RollbackException {
		UserBean[] users = match();
		Arrays.sort(users);
		return users;
	}

	/**
	 * Method to retrieve details of a user, given an e-mail ID.
	 * 
	 * @param email
	 *            of the user whose details are to be searched
	 * @return UserBean object representing the user whose email matches the
	 *         parameter, email
	 * @throws RollbackException
	 */
	public UserBean read(String email) throws RollbackException {
		UserBean[] bean = match(MatchArg.equals("email", email));
		if (bean != null && bean.length > 0) {
			return bean[0];
		}
		return null;
	}

	/**
	 * Method to check if a given e-mail ID exists in the database.
	 * 
	 * @param email
	 *            that has to searched in the database
	 * @return boolean value indicating whether an email ID is present
	 * @throws RollbackException
	 */
	public boolean isEmailPresent(String email) throws RollbackException {
		UserBean[] users = match(MatchArg.equals("email", email));
		if (users.length > 0)
			return true;
		else
			return false;
	}

	/**
	 * @param userId
	 *            of the user whose email ID has to be retrieved
	 * @return the email ID of the user whose user ID corresponds to userId
	 * @throws RollbackException
	 */
	public String getEmailId(String userId) throws RollbackException {
		int userID = Integer.parseInt(userId);
		UserBean[] users = match(MatchArg.equals("userId", userID));
		if (users.length > 0) {
			String email = users[0].getEmail();
			return email;
		} else {
			return null;
		}
	}

	/**
	 * @param email
	 *            of the user whose password is being changed
	 * @param password
	 *            that has to be updated to the user
	 * @return UserBean instance after updating the user and the new password to
	 *         the database
	 * @throws RollbackException
	 */
	public UserBean setPassword(String email, String password) throws RollbackException {
		try {
			Transaction.begin();
			UserBean user = read(email);

			if (user == null) {
				throw new RollbackException("User no longer exists");
			}

			user.encodePassword(password);

			update(user);
			Transaction.commit();

			return user;
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}
}
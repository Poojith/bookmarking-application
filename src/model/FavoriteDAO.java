/**
 * Homework 4 solution for favorite URLs/websites.
 * @author Poojith Kumar Jain (poojithj@andrew.cmu.edu)
 * Date: 07 December, 2016
 * Course: 08-672 (J2EE Web Application Development)
 */

package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.FavoriteBean;

public class FavoriteDAO extends GenericDAO<FavoriteBean> {
	public FavoriteDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(FavoriteBean.class, tableName, cp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.genericdao.GenericDAO#create(java.lang.Object)
	 */
	public void create(FavoriteBean favorite) throws RollbackException {
		try {
			Transaction.begin();
			super.create(favorite);
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}

	/**
	 * Method to retrieve the list of favorites associated with a particular
	 * user.
	 * 
	 * @param userId
	 *            associated with a FavoriteBean object
	 * @return FavoriteBean[] array of FavoriteBean objects whose user ID is
	 *         equal to userId
	 * @throws RollbackException
	 */

	public FavoriteBean[] getUserFavorites(int userId) throws RollbackException {
		FavoriteBean[] favoritesList = match(MatchArg.equals("userId", userId));
		return favoritesList;
	}

	/**
	 * Method to retrieve a FavoriteBean object, given a particular favoriteId.
	 * 
	 * @param favoriteId
	 *            associated with an instance of FavoriteBean
	 * @return FavoriteBean object whose favorite ID is equal to favoriteId
	 * @throws RollbackException
	 */
	public FavoriteBean getFavoriteBean(int favoriteId) throws RollbackException {
		FavoriteBean[] beans = match(MatchArg.equals("favoriteId", favoriteId));
		if (beans != null && beans.length > 0) {
			return beans[0];
		}
		return null;
	}

	/**
	 * Method to implement the functionality of incrementing click count based
	 * on the respective link that was clicked.
	 * 
	 * @param favorite
	 *            object of FavoriteBean class
	 * @throws RollbackException
	 */
	public void updateClick(FavoriteBean favorite) throws RollbackException {
		try {
			Transaction.begin();
			if (favorite != null) {
				int count = favorite.getClickCount() + 1;
				favorite.setClickCount(count);
				super.update(favorite);
			}
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}

	/**
	 * @param id
	 *            of the favorite that has to be deleted
	 * @param userId
	 *            of the user who owns the favorite
	 * @throws RollbackException
	 */
	public void deleteFavorite(int id, int userId) throws RollbackException {
		try {
			Transaction.begin();
			FavoriteBean bean = read(id);

			if (bean == null) {
				throw new RollbackException("Favorite does not exist: ID=" + id);
			}

			if (userId != (bean.getUserId())) {
				throw new RollbackException("Favorite not owned by " + userId);
			}

			delete(id);
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}
}

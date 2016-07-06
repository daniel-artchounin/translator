package dao;

import java.util.ArrayList;

import beans.Content;


/* The interface of the class which interacts with 
 * the database to get or delete some contents.
 */
public interface ContentsManagementDao extends Dao {
	public ArrayList<Content> getContents() throws DaoException;
	public void deleteContent(int contentId) throws DaoException;
}

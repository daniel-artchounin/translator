package dao;

import java.util.ArrayList;

import beans.Content;
import beans.Language;

/* The interface of the class which interacts with 
 * the database to import some translations.
 */
public interface ImportContentDao extends Dao {
	public ArrayList<Language> getLanguages() throws DaoException;
	public int getPartId(int contentId, String partBeginning) throws DaoException;
	public void addContent(Content content, int languageId) throws DaoException;
	public int getContentId(String contentName) throws DaoException;
}

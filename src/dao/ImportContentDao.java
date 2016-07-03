package dao;

import java.sql.Time;
import java.util.ArrayList;

import beans.Content;
import beans.Language;

public interface ImportContentDao extends Dao {
	public ArrayList<Language> getLanguages() throws DaoException;
	public int getPartId(int contentId, Time partBeginning) throws DaoException;
	public void addContent(Content content, int languageId) throws DaoException;
	public int getContentId(String contentName) throws DaoException;
}

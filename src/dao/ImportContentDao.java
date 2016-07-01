package dao;

import java.sql.Time;
import java.util.ArrayList;

import beans.Language;

public interface ImportContentDao {
	public ArrayList<Language> getLanguages() throws DaoException;
	public int getContentId(String contentName) throws DaoException;
	public int getPartId(int contentId, Time partBeginning) throws DaoException;
}

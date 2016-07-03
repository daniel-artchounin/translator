package dao;

import java.util.ArrayList;

import beans.Content;

public interface ContentsManagementDao extends Dao {
	public ArrayList<Content> getContents() throws DaoException;
	public void deleteContent(int contentId) throws DaoException;
}

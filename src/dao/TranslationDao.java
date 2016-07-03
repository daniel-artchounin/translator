package dao;

import beans.Content;

public interface TranslationDao {
	public Content getContent(int contentId, int languageId) throws DaoException;
}

package dao;

import beans.Content;


/* The base interface to deal with the translations. */
public interface TranslationDao extends Dao {
	public Content getContent(int contentId, int languageId) throws DaoException;
}

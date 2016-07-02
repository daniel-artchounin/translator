package dao;

import beans.Content;

public interface ExportTranslationDao {
	public Content getContent(int contentId, int languageId) throws DaoException;
}

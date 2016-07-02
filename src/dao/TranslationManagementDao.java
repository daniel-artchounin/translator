package dao;


public interface TranslationManagementDao {
	public void activateTranslation (int contentId, int languageId) throws DaoException;
	public void deactivateTranslation (int contentId, int languageId) throws DaoException;
}

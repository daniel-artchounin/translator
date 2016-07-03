package dao;


public interface TranslationManagementDao extends TranslationDao {
	public void activateTranslation (int contentId, int languageId) throws DaoException;
	public void deactivateTranslation (int contentId, int languageId) throws DaoException;
	public int getNumberOfTranslations(int contentId) throws DaoException;
	public boolean isModifiableTranslation(int contentId, int languageId) throws DaoException; 
}

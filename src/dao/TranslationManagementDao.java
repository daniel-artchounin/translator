package dao;

import java.util.ArrayList;

import beans.Language;


public interface TranslationManagementDao extends TranslationDao {
	public void activateTranslation (int contentId, int languageId) throws DaoException;
	public void deactivateTranslation (int contentId, int languageId) throws DaoException;
	public int getNumberOfTranslations(int contentId) throws DaoException;
	public boolean isModifiableTranslation(int contentId, int languageId) throws DaoException; 
	public ArrayList<Language> getContentActivatedLanguages(int contentId) throws DaoException ;
}
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;


public class TranslationManagementDaoImpl implements TranslationManagementDao {
    private DaoFactory daoFactory;

    TranslationManagementDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
	
    @Override
	public void activateTranslation (int contentId, int languageId) throws DaoException {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO ContentLanguage(content, language) "
        		+ "VALUES(?, ?);";
        String activateTranslationErrorMessage = "Impossible d'effectuer l'action demandée.";
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        try{
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setInt(1, contentId);
            preparedStatement.setInt(2, languageId);
            int result = preparedStatement.executeUpdate();            
            if(result == 0){
            	throw new DaoException(activateTranslationErrorMessage);
            }
            connexion.commit();
        } catch (SQLException e) {
            throw new DaoException(databaseErrorMessage);
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException(databaseErrorMessage);
            }
        }
	}	
    
    @Override
	public void deactivateTranslation (int contentId, int languageId) throws DaoException {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = "DELETE FROM ContentLanguage "
        		+ "WHERE content = ? AND language = ?;";
        String deactivateTranslationErrorMessage = "Impossible d'effectuer l'action demandée.";
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        int numberOfTranslations = this.getNumberOfTranslations(contentId);
        if( numberOfTranslations == 0 ){
        	throw new DaoException("Le contenu dont on souhaite désactiver une traduction "
        			+ "doit en posséder au moins deux.");
        }
        try{
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setInt(1, contentId);
            preparedStatement.setInt(2, languageId);
            int result = preparedStatement.executeUpdate();            
            if(result == 0){
            	throw new DaoException(deactivateTranslationErrorMessage);
            }
            connexion.commit();
        } catch (SQLException e) {
            throw new DaoException(databaseErrorMessage);
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException(databaseErrorMessage);
            }
        }
	}	
    
    @Override
	public int getNumberOfTranslations(int contentId) throws DaoException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        int numberOfTranslations = 0;
        String query = "SELECT COUNT(*) AS numberOfTranslations "
        		+ "FROM ContentLanguage "
        		+ "WHERE content = ?;";
        try{
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setInt(1, contentId);
            ResultSet result = preparedStatement.executeQuery();
            boolean isNext = result.next();
            if( !isNext ){
            	throw new DaoException("Action momentanément impossible, veuillez contacter un administrateur");
            } 
        	numberOfTranslations = result.getInt("numberOfTranslations");
        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la base de données " + e.getMessage());
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données");
            }
        }
        return numberOfTranslations;
    }
	
}
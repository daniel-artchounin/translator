package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;


import beans.Language;
import beans.PartTranslation;


/* The implementation of the class which interacts with 
 * the database to manage some translations.
 */
public class TranslationManagementDaoImpl extends TranslationDaoImpl implements TranslationManagementDao {

    TranslationManagementDaoImpl(DaoFactory daoFactory) {
    	super(daoFactory);
    }
	
    /* To activate a translation in the database. */
    @Override
	public void activateTranslation (int contentId, int languageId) throws DaoException {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO ContentLanguage(content, language) "
        		+ "VALUES(?, ?);";
        String activateTranslationErrorMessage = "Impossible d'effectuer l'action demandée.";
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        try{
            connexion = this.daoFactory.getConnection();
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
    
    /* To deactivate a translation in the database. */
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
    
    /* To get the number of activated translation(s) of a specific content. */
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
    
    /* To know if a translation of a content is modifiable (not activated). */
    @Override
    public boolean isModifiableTranslation(int contentId, int languageId) throws DaoException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        int numberFound = 0;
        String query = "SELECT COUNT(*) AS numberFound "
        		+ "FROM ContentLanguage "
        		+ "WHERE content = ? AND language = ?;";
        try{
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setInt(1, contentId);
            preparedStatement.setInt(2, languageId);
            ResultSet result = preparedStatement.executeQuery();
            boolean isNext = result.next();
            if( !isNext ){
            	throw new DaoException("Action momentanément impossible, veuillez contacter un administrateur");
            } 
            numberFound = result.getInt("numberFound");
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
        if( numberFound == 0 ){
        	return true;
        } else {
        	return false;
        }
    }
    
    /* To get the activated languages of a content in the database. */
	@Override
	public ArrayList<Language> getContentActivatedLanguages(int contentId) throws DaoException {		
		ArrayList<Language> languages = new ArrayList<Language>();
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        /* Query to get the activated languages of a content in the database */
        String query = "SELECT L.id AS languageId, L.language AS languageName "
        		+ "FROM ContentLanguage CL, Language L "
        		+ "WHERE CL.content = ? AND CL.language = L.id;";
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        try{
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setInt(1, contentId);
            ResultSet result = preparedStatement.executeQuery();
            while ( result.next() ) {
            	/* Here, we get the content of each tuple */
            	int languageId = result.getInt("languageId");
                String languageName = result.getString("languageName");
                /* Then, we add it to our content */
                languages.add(new Language(languageId, languageName));    
            }
        } catch (SQLException e) {
            throw new DaoException(databaseErrorMessage + ": " + e.getMessage());
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException(databaseErrorMessage + ": " + e.getMessage());
            }
        }
        return languages;
	}
	
	/* To update the parts of a translation. */
	@Override
	public void updateTranslation(ArrayList<PartTranslation> partTranslations) throws DaoException {
		Connection connexion = null;
		String databaseErrorMessage = "Impossible de communiquer avec la base de données";
		try{			
		    connexion = this.daoFactory.getConnection();
            for( PartTranslation partTranslation : partTranslations ){
            	this.updatePart(connexion, partTranslation);
            }
            connexion.commit();
		} catch (SQLException e) {
		    throw new DaoException(databaseErrorMessage + ": " + e.getMessage());
		}
		finally {
		    try {
		        if (connexion != null) {
		            connexion.close();  
		        }
		    } catch (SQLException e) {
		        throw new DaoException(databaseErrorMessage + ": " + e.getMessage());
		    }
		}
    }

	/* To update a specific part of a translation. */
    protected void updatePart(Connection connexion, PartTranslation partTranslation) throws DaoException{
		PreparedStatement preparedStatement = null;
		String query = "UPDATE PartTranslation "
				+ "SET content=? "
				+ "WHERE part=? AND language=?;";
		String updatePartErrorMessage = "Impossible de mettre à jour une partie";	
		try{
		    preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
		    preparedStatement.setString(1, partTranslation.getContent());
		    preparedStatement.setInt(2, partTranslation.getPart());
		    preparedStatement.setInt(3, partTranslation.getLanguage());		    
		    preparedStatement.executeUpdate();         
		} catch (SQLException e) {		
		    throw new DaoException(updatePartErrorMessage + ": " + e.getMessage());
		}
    }
	
}
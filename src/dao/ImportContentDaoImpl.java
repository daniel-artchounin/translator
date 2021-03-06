package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import beans.Content;
import beans.ContentPart;
import beans.Language;


/* The implementation of the class which interacts with 
 * the database to import some translations.
 */
public class ImportContentDaoImpl extends DaoImpl implements ImportContentDao {

	ImportContentDaoImpl(DaoFactory daoFactory) {
        super(daoFactory);
    }
    
	/* To add a content in the database. */
    @Override
    public void addContent(Content content, int languageId) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		String query = "INSERT INTO Content(name) "
				+ "VALUES (?);";
		String databaseErrorMessage = "Impossible de communiquer avec la base de données";
		String contentErrorMessage = "Impossible d'ajouter le contenu " + content.getName();		
		ArrayList<Language> languages = null;
		int contentId = 0;
		try{			
		    connexion = this.daoFactory.getConnection();
		    languages = this.getLanguages(); // Here, we get the existing languages in the database
		    preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
		    preparedStatement.setString(1, content.getName());
		    int result = preparedStatement.executeUpdate();		    
            if(result == 0){
            	throw new DaoException(contentErrorMessage);
            }
            contentId = this.getContentId(connexion, content.getName());
            for( ContentPart contentPart : content.getParts() ){
            	this.addPart(connexion, contentId, contentPart, languageId, languages);
            }
            this.addContentLanguage(connexion, contentId, languageId);
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

    /* To add a content's part in the database. */
    protected void addPart(Connection connexion, int contentId, ContentPart contentPart, int languageId, ArrayList<Language> languages) throws DaoException{
		PreparedStatement preparedStatement = null;
		String query = "INSERT INTO Part(content, beginning, end) "
				+ "VALUES (?, ?, ?);";
		String databaseErrorMessage = "Impossible de communiquer avec la base de données";
		String contentErrorMessage = "Impossible d'ajouter une partie";	
		int partId = 0;
		try{
		    preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
		    preparedStatement.setInt(1, contentId);
		    preparedStatement.setString(2, contentPart.getBeginning());
		    preparedStatement.setString(3, contentPart.getEnd());		    
		    int result = preparedStatement.executeUpdate();		   
            if(result == 0){
            	throw new DaoException(contentErrorMessage);
            }            
            partId = this.getPartId(connexion, contentId, contentPart.getBeginning());
            for( Language language : languages ){
            	this.addPartTranslation(connexion, partId, language, contentPart.getPartContent(), languageId);
            }            
		} catch (SQLException e) {		
		    throw new DaoException(databaseErrorMessage + ": " + e.getMessage());
		}
    }

    /* To add a part's translation in the database. */
    protected void addPartTranslation(Connection connexion, int part, Language currentLanguage, String content, int translatedLanguageId) throws DaoException{
    	PreparedStatement preparedStatement = null;
		String query = "INSERT INTO PartTranslation(part, language, content) "
				+ "VALUES (?, ?, ?);";
		String databaseErrorMessage = "Impossible de communiquer avec la base de données";
		String partTranslationErrorMessage = "Impossible d'ajouter une traduction";
		int currentLanguageId = currentLanguage.getId();
		try{
		    preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
		    preparedStatement.setInt(1, part);
		    preparedStatement.setInt(2, currentLanguageId);
		    if( currentLanguageId == translatedLanguageId ){
		    	preparedStatement.setString(3, content);
		    } else {
		    	preparedStatement.setString(3, "");
		    }
		    int result = preparedStatement.executeUpdate();		    
            if(result == 0){
            	throw new DaoException(partTranslationErrorMessage);
            }		                
		} catch (SQLException e) {		
		    throw new DaoException(databaseErrorMessage + ": " + e.getMessage());
		}
    }
    
    /* To save in the database that a translation is active. */
    protected void addContentLanguage(Connection connexion, int content, int language) throws DaoException{
		PreparedStatement preparedStatement = null;
		String query = "INSERT INTO ContentLanguage(content, language) "
				+ "VALUES (?, ?);";
		String databaseErrorMessage = "Impossible de communiquer avec la base de données";
		String contentLanguageErrorMessage = "Impossible d'associer une langue "
				+ "à une traduction de contenu.";
		try{
		    preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
		    preparedStatement.setInt(1, content);
		    preparedStatement.setInt(2, language);		    
		    int result = preparedStatement.executeUpdate();		    
            if(result == 0){
            	throw new DaoException(contentLanguageErrorMessage);
            }		                
		} catch (SQLException e) {		
		    throw new DaoException(databaseErrorMessage + ": " + e.getMessage());
		}
    }
    
    /* To get the languages in the database. */
    @Override
	public ArrayList<Language> getLanguages() throws DaoException {
    	ArrayList<Language> languages = new ArrayList<Language>();
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try{
            connexion = this.daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement("SELECT id, language "
            		+ "FROM Language "
            		+ "ORDER BY id;");
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
            	String language = result.getString("language");
            	int id = result.getInt("id");
            	Language tmpLanguage = new Language(id, language);                
            	languages.add(tmpLanguage);
            }
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
        return languages;
    }
    
    /* To get a content's id using its name. */
    @Override
	public int getContentId(String contentName) throws DaoException {
		Connection connexion = null;
		int contentId = 0;
        try{
            connexion = this.daoFactory.getConnection();
            contentId = this.getContentId(connexion, contentName);
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
        return contentId;
    }
    
    /* To get a content's id using its name. */
    protected int getContentId(Connection connexion, String contentName) throws DaoException {
    	int contentId = 0;
        PreparedStatement preparedStatement = null;
        String contentIdErrorMessage = "Contenu inexistant : veuillez contacter un administrateur.";
        String query = "SELECT C.id AS id "
        		+ "FROM Content C "
        		+ "WHERE C.name = ?;";
        boolean atLeastOneResult = false;
        try{
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setString(1, contentName);
            ResultSet result = preparedStatement.executeQuery();
            atLeastOneResult = result.next();
            if( atLeastOneResult ){
            	contentId = result.getInt("id");
            } else {
            	throw new DaoException(contentIdErrorMessage);
            }
        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la base de données " + e.getMessage());
        }
        return contentId;
    }
    
    /* To get a part's id. */
    @Override
	public int getPartId(int contentId, String partBeginning) throws DaoException {
		Connection connexion = null;
		int partId = 0;
        try{
            connexion = this.daoFactory.getConnection();
            partId = this.getPartId(connexion, contentId, partBeginning);
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
        return partId;
    }
	
    /* To get a part's id. */
	protected int getPartId(Connection connexion, int contentId, String partBeginning) throws DaoException {
    	int partId = 0;
        PreparedStatement preparedStatement = null;
        String partIdErrorMessage = "Partie inexistante : veuillez contacter un administrateur.";
        String query = "SELECT P.id AS id "
        		+ "FROM Part P "
        		+ "WHERE P.content = ? AND P.beginning = ?;";
        boolean atLeastOneResult = false;
        try{
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setInt(1, contentId);
            preparedStatement.setString(2, partBeginning);
            ResultSet result = preparedStatement.executeQuery();
            atLeastOneResult = result.next();
            if( atLeastOneResult ){
            	partId = result.getInt("id");
            } else {
            	throw new DaoException(partIdErrorMessage);
            }
        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la base de données " + e.getMessage());
        }
        return partId;
    }
    
}
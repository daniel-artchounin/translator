package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import beans.Content;
import beans.Language;


/* The implementation of the class which interacts 
 * with the database to get or delete some contents.
 */
public class ContentsManagementDaoImpl extends DaoImpl implements ContentsManagementDao {

	ContentsManagementDaoImpl(DaoFactory daoFactory) {
        super(daoFactory);
    }

	/* To get the contents in the database. */
	@Override
	public ArrayList<Content> getContents() throws DaoException {
		ArrayList<Content> contents = new ArrayList<Content>();
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        /* Query to get the contents and their translations in the database */
        String query = "SELECT * FROM ( "
        		+ "SELECT C.id AS contentId, C.name AS contentName, L.id AS languageId, L.language  AS languageName, 1 AS exportableTranslation "
        		+ "FROM Content C, ContentLanguage CL, Language L "
        		+ "WHERE C.id = CL.content AND CL.language = L.id "
        		+ "UNION ALL "
        		+ "SELECT R1.contentId AS contentId, R1.contentName AS contentName, R1.languageId AS languageId, R1.languageName AS languageName, R1.exportableTranslation AS exportableTranslation "
        		+ "FROM "
        		+ "( "
        		+ "SELECT C.id AS contentId, C.name AS contentName, L.id AS languageId, L.language  AS languageName, 0 AS exportableTranslation "
        		+ "FROM Content C, Language L "
        		+ ") R1 LEFT OUTER JOIN ( "
        		+ "SELECT C.id AS contentId, C.name AS contentName, L.id AS languageId, L.language  AS languageName, 0 AS exportableTranslation "
        		+ "FROM Content C, ContentLanguage CL, Language L "
        		+ "WHERE C.id = CL.content AND CL.language = L.id "
        		+ ") R2 "
        		+ "ON R1.contentId = R2.contentId AND R1.languageId = R2.languageId "
        		+ "WHERE R2.contentId IS NULL "
        		+ ") R3 "
        		+ "ORDER BY R3.contentName, R3.languageName;";
        String previousContentName = ""; // To memorize the previous content's name in each iteration
        Content tmpContent = null; // The current content
        String databaseErrorMessage = "Impossible de communiquer avec la base de données.";
        int i = 0; // Iterator
        /* Contains the number of deactivated translations of our current content */
        int numberOfExportableTranslations = 0; 
        try{
            connexion = this.daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            while ( result.next() ) {
            	i += 1;
            	/* Here we get the content of each tuple */
            	int contentId = result.getInt("contentId");
            	String contentName = result.getString("contentName");
                int languageId = result.getInt("languageId");
                String languageName = result.getString("languageName");
                boolean exportableTranslation = result.getBoolean("exportableTranslation");                
                if( !previousContentName.equals(contentName) ){
                	/* If we are dealing with a new content */
                	if( i != 1 ){
                		/* If it's not the first content */
                		if( numberOfExportableTranslations >= 2 ){
                			/* If it has at least two exportable translations */
                			tmpContent.setHasAtLeastTwoExportableTranslations(true);
                		} else {
                			tmpContent.setHasAtLeastTwoExportableTranslations(false);
                		}                		
                		contents.add(tmpContent); // We save our previous content
                	}                	
                	/* We create a new content */
                	tmpContent = new Content(contentId, contentName);
                	/* We update the previous content's name */
                	previousContentName = contentName;
                	numberOfExportableTranslations = 0;
                }                
                if( exportableTranslation ){
                	/* If we are dealing with an exportable translation */
                	numberOfExportableTranslations++;
                }                
                /* We create a new translation */
                Language tmpLanguage = new Language(languageId, languageName, exportableTranslation);
                /* We add it to our current content */
                tmpContent.getLanguages().add(tmpLanguage);
            }
            /* We have finished to read every tuple in the result */
            if(i >= 1){
        		if( numberOfExportableTranslations >= 2 ){
        			/* If the last content has at least two exportable translations */
        			tmpContent.setHasAtLeastTwoExportableTranslations(true);
        		} else {
        			tmpContent.setHasAtLeastTwoExportableTranslations(false);
        		}
            	contents.add(tmpContent); // We save the last content
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
        return contents;
	}
	
	/* To delete a specific content in the database. */
    @Override
	public void deleteContent(int contentId) throws DaoException {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = "DELETE FROM Content "
        		+ "WHERE id = ?;";
        String contentErrorMessage = "Impossible de supprimer le contenu.";
        String databaseErrorMessage = "Impossible de communiquer avec la base de données.";
        try{
            connexion = this.daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setInt(1, contentId);
            int result = preparedStatement.executeUpdate();
            connexion.commit();
            if(result == 0){
            	throw new DaoException(contentErrorMessage);
            }
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
	
}
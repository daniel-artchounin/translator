package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import beans.Content;
import beans.ContentPart;

public class TranslationDaoImpl extends DaoImpl implements TranslationDao {
	
    TranslationDaoImpl(DaoFactory daoFactory) {
        super(daoFactory);
    }
    
	@Override
	public Content getContent(int contentId, int languageId) throws DaoException {		
		Content content = new Content();
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        /* Query to get the parts of a content in the database */
        String query = "SELECT C.name AS contentName, P.id AS partId, P.beginning AS partBeginning, P.end AS partEnd, PT.content AS partContent "
        		+ "FROM Content C, Part P, PartTranslation PT "
        		+ "WHERE C.id = ? AND C.id = P.content AND P.id = PT.part AND PT.language = ?;";
        String databaseErrorMessage = "Impossible de communiquer avec la base de données";
        boolean firstResult = true; // To know if we are dealing with the first result
        try{
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setInt(1, contentId);
            preparedStatement.setInt(2, languageId);
            ResultSet result = preparedStatement.executeQuery();
            while ( result.next() ) {
            	/* Here we get the content of each tuple */
            	String contentName = result.getString("contentName");
            	int partId = result.getInt("partId");
                Time partBeginning = result.getTime("partBeginning");
                Time partEnd = result.getTime("partEnd");
                String partContent = result.getString("partContent");
            	if(firstResult){
            		content.setName(contentName);
            	}
            	content.getParts().add(new ContentPart(partId, contentId, partBeginning, partEnd, partContent));    
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
        return content;
	}
}

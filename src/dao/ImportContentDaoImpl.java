package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import beans.Language;


public class ImportContentDaoImpl implements ImportContentDao {
    private DaoFactory daoFactory;

    ImportContentDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
	public ArrayList<Language> getLanguages() throws DaoException {
    	ArrayList<Language> languages = new ArrayList<Language>();
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try{
            connexion = daoFactory.getConnection();
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
    
    @Override
	public int getContentId(String contentName) throws DaoException {
    	int contentId = 0;
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT C.id AS id "
        		+ "FROM Content C "
        		+ "WHERE C.name = ?;";
        boolean atLeastOneResult = false;
        try{
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setString(1, contentName);
            ResultSet result = preparedStatement.executeQuery();
            atLeastOneResult = result.next();
            if( atLeastOneResult ){
            	contentId = result.getInt("id");
            } else {
            	throw new DaoException("Langue inexistante : veuillez contacter un administrateur.");
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
        return contentId;
    }
    
    @Override
	public int getPartId(int contentId, Time partBeginning) throws DaoException {
    	int partId = 0;
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT P.id AS id "
        		+ "FROM Part P "
        		+ "WHERE P.content = ? AND P.beginning = ?;";
        boolean atLeastOneResult = false;
        try{
            connexion = daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connexion.prepareStatement(query);
            preparedStatement.setInt(1, contentId);
            preparedStatement.setTime(2, partBeginning);
            ResultSet result = preparedStatement.executeQuery();
            atLeastOneResult = result.next();
            if( atLeastOneResult ){
            	partId = result.getInt("id");
            } else {
            	throw new DaoException("Partie inexistante : veuillez contacter un administrateur.");
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
        return partId;
    }
    
}
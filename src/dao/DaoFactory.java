package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.Connection;


/* This class is the factory of our DAO model. */
public class DaoFactory {
    private static final String FILE_PROPERTIES = "/dao/dao.properties";
    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_DRIVER = "driver";
    private static final String PROPERTY_USER_NAME = "username";
    private static final String PROPERTY_PASSWORD = "password";
	
	private String url;
	private String username;
	private String password;
	
    DaoFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

	/* To get an instance of the factory. */
    public static DaoFactory getInstance() throws DAOConfigurationException {
    	Properties properties = new Properties();
        String url;
        String driver;
        String userName;
        String password;
        
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream fileProperties = classLoader.getResourceAsStream(FILE_PROPERTIES);

        if ( fileProperties == null ) {
            throw new DAOConfigurationException("Le fichier properties " + FILE_PROPERTIES + " est introuvable." );
        }

        try {
            properties.load( fileProperties );
            url = properties.getProperty( PROPERTY_URL );
            driver = properties.getProperty( PROPERTY_DRIVER );
            userName = properties.getProperty( PROPERTY_USER_NAME );
            password = properties.getProperty( PROPERTY_PASSWORD );
        } catch ( IOException e ) {
            throw new DAOConfigurationException( "Impossible de charger le fichier properties " + FILE_PROPERTIES + ".");
        }
        
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {

        }
        
        DaoFactory instance = new DaoFactory(url, userName, password);
        return instance;
    }

    /* To get a connection to the database. */
    public Connection getConnection() throws SQLException {
        Connection connexion =  (Connection) DriverManager.getConnection(url, username, password);
        connexion.setAutoCommit(false);
        return connexion;
    }

    public ImportContentDao getImportContentDao() {
        return new ImportContentDaoImpl(this);
    }
    
    public ContentsManagementDao getContentsManagementDao() {
        return new ContentsManagementDaoImpl(this);
    }
    
    public ExportTranslationDao getExportTranslationDao() {
        return new ExportTranslationDaoImpl(this);
    }
    
    public TranslationManagementDao getTranslationManagementDao() {
        return new TranslationManagementDaoImpl(this);
    }

}

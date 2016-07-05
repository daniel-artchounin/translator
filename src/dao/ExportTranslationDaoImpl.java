package dao;


/* The implementation of the class which interacts with the database 
 * to export some translations.
 * */
public class ExportTranslationDaoImpl extends TranslationDaoImpl implements ExportTranslationDao {
	
    ExportTranslationDaoImpl(DaoFactory daoFactory) {
    	super(daoFactory);
    }    
}
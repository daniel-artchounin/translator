package dao;


/* This class allows us to throw some exceptions linked to the configuration of our DAO model */
public class DAOConfigurationException extends Exception {
	private static final long serialVersionUID = 1L;

	public DAOConfigurationException(String message){
        super(message);
    }
}
package dao;

/* This class allow us to throw some exception linked to the configuration of the dao model */
public class DAOConfigurationException extends Exception {
	private static final long serialVersionUID = 1L;

	public DAOConfigurationException(String message){
        super(message);
    }
}
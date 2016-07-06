package dao;


/* This class allows us to throw some exception linked to our DAO model */
public class DaoException extends Exception {
	private static final long serialVersionUID = -3806089201695664347L;

	public DaoException(String message) {
        super(message);
    }
}

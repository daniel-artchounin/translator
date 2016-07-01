package dao;

/* This class allow us to throw some exception linked to the dao model */
public class DaoException extends Exception {
	private static final long serialVersionUID = -3806089201695664347L;

	public DaoException(String message) {
        super(message);
    }
}

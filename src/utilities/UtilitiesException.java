package utilities;

/* This class allow us to throw some exception linked to our SRT handler */
public class UtilitiesException extends Exception {
	private static final long serialVersionUID = -3806089201695664347L;

	public UtilitiesException(String message) {
        super(message);
    }
}

package utilities;


/* 
 * This class allows us to throw some exceptions linked 
 * to our interaction with some SRT files
 */
public class UtilitiesException extends Exception {
	private static final long serialVersionUID = -3806089201695664347L;

	public UtilitiesException(String message) {
        super(message);
    }
}

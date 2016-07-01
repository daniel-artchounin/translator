package utilities;

/* This class allow us to throw some exception linked to our SRT handler */
public class SRTHandlerException extends Exception {
	private static final long serialVersionUID = -3806089201695664347L;

	public SRTHandlerException(String message) {
        super(message);
    }
}

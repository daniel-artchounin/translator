package beans;

/* This class allow us to throw some exception linked to the beans */
public class BeanException extends Exception {

	private static final long serialVersionUID = 1L;

	public BeanException(String message) {
        super(message);
    }
}

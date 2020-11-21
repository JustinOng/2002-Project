package mystars.exceptions;

/**
 * <h1>Class: UIException</h1>
 * 
 * This class inherits from Java's built in Runtime Exception class to handle
 * general exceptions that may occur during the running of the user interface of
 * this program.
 */
public class UIException extends RuntimeException {
	/**
	 * Serialization of the course ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * User interface exception class.
	 */
	public UIException() {
	}

	/**
	 * Overloading of user interface exception class that accepts a custom exception
	 * message.
	 * 
	 * @param msg The message generated from the exception.
	 */
	public UIException(String msg) {
		super(msg);
	}
}

package mystars.exceptions;

/**
 * <h1>Class: AppException</h1>
 * 
 * This class inherits from Java's built in Exception class to handle general exceptions
 * that may occur during the running of this program.
 */
public class AppException extends Exception {
	/**
	 * Serialization of the course ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Application exception class.
	 */
	public AppException() {}
	
	/**
	 * Overloading of application exception class that accepts a custom exception message.
	 * @param msg The message generated from the exception.
	 */
	public AppException(String msg) {
		super(msg);
	}
}

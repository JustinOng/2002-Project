package mystars.exceptions;

public class AppException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public AppException() {}
	
	public AppException(String msg) {
		super(msg);
	}
}

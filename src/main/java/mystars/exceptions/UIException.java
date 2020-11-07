package mystars.exceptions;

public class UIException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UIException() {}
	
	public UIException(String msg) {
		super(msg);
	}
}

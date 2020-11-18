package mystars.forms;

/**
 * <h1>Class: TextResponse</h1>
 * 
 * This class manages the text in the form being displayed in the user interface.
 */
public class TextResponse {
	/**
	 * The text to be displayed.
	 */
	private final String text;
	
	/**
	 * Sets the text to be displayed.
	 * 
	 * @param text The text to be displayed.
	 */
	public TextResponse(String text) {
		this.text = text;
	}
	
	/**
	 * Returns the text to be displayed.
	 * 
	 * @return The text to be displayed.
	 */
	public String getText() {
		return text;
	}
}

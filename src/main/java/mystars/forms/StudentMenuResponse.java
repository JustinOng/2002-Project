package mystars.forms;

/**
 * <h1>Class: StudentMenuResponse</h1>
 * 
 * This class manages the form for students with the relevant options.
 */
public class StudentMenuResponse {
	/**
	 *Enum containing the options for students to anage their courses.
	 */
	public enum Selected {
		Register,
		Drop,
		Change,
		Swop
	};
	
	/**
	 * The selected object.
	 */
	private final Selected selected;
	
	/**
	 * Sets the selected object.
	 * 
	 * @param selected The selected object.
	 */
	public StudentMenuResponse(Selected selected) {
		this.selected = selected;
	}
	
	/**
	 * Returns the selected object.
	 * 
	 * @return The selected object.
	 */
	public Selected getSelected() {
		return selected;
	}
}

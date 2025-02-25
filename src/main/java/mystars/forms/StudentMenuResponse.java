package mystars.forms;

/**
 * This class manages the form for students with the relevant options.
 */
public class StudentMenuResponse {
	/**
	 * Enum containing the options for students to manage their courses.
	 */
	public enum Selected {
		ListRegistered, ListVacancies, ListIndexes, Register, Drop, Change, Swop, Logout
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

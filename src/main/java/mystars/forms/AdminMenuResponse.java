package mystars.forms;

/**
 * <h1>Class: AdminMenuResponse</h1>
 * 
 * This class manages the form for administrators with relevant options.
 */
public class AdminMenuResponse {
	/**
	 * Enum containing the options available to administrators.
	 */
	public enum Selected {
		EditStudentAccessPeriod, CreateStudent, CreateCourse, ManageCourses, ListVacancies, ListStudents, Logout
	};

	/**
	 * Stores the selected option.
	 */
	private final Selected selected;

	/**
	 * Sets the selected option.
	 * 
	 * @param selected The selected option.
	 */
	public AdminMenuResponse(Selected selected) {
		this.selected = selected;
	}

	/**
	 * Returns the selected option.
	 * 
	 * @return The selected option.
	 */
	public Selected getSelected() {
		return selected;
	}
}

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
		EditStudentAccessPeriod, CreateStudent, CreateCourse, ManageCourses, ListVacancies, ListStudents
	};

	/**
	 * Stores the selected object.
	 */
	private final Selected selected;

	/**
	 * Sets the selected object.
	 * 
	 * @param selected The selected object.
	 */
	public AdminMenuResponse(Selected selected) {
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

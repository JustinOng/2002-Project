package mystars.forms;

/**
 * This class manages the form for the management of courses with the relevant
 * options.
 */
public class CourseManagementResponse {
	/**
	 * Enum containing the options for managing courses.
	 */
	public enum Selected {
		CreateIndex, ManageIndexes, ListStudents
	};

	/**
	 * The selected object.
	 */
	private final Selected selected;

	/**
	 * The course name.
	 */
	private final String courseName;

	/**
	 * Sets the course name and selected object.
	 * 
	 * @param courseName The course name.
	 * @param selected   The selected object.
	 */
	public CourseManagementResponse(String courseName, Selected selected) {
		this.courseName = courseName;
		this.selected = selected;
	}

	/**
	 * Returns the course name.
	 * 
	 * @return The course name.
	 */
	public String getCourseName() {
		return courseName;
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

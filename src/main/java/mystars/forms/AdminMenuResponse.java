package mystars.forms;

public class AdminMenuResponse {
	public enum Selected {
		EditStudentAccessPeriod, CreateStudent, CreateCourse, ManageCourses
	};

	private final Selected selected;

	public AdminMenuResponse(Selected selected) {
		this.selected = selected;
	}

	public Selected getSelected() {
		return selected;
	}
}

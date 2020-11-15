package mystars.forms;

public class CourseManagementResponse {
	public enum Selected {
		CreateIndex, ManageIndexes, ListStudents
	};

	private final Selected selected;
	private final String courseName;

	public CourseManagementResponse(String courseName, Selected selected) {
		this.courseName = courseName;
		this.selected = selected;
	}
	
	public String getCourseName() {
		return courseName;
	}

	public Selected getSelected() {
		return selected;
	}
}

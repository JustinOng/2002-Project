package mystars.forms;

import java.util.List;

public interface IUserInterface {
	public LoginResponse renderLoginForm();
	public StudentMenuResponse renderStudentMenuForm(List<String> courses);
	public AdminMenuResponse renderAdminMenuForm();
	public IndexSwopResponse renderIndexSwopForm();
	public TextResponse renderItemSelectorForm(String title, List<String> items);
	public CreateStudentResponse renderCreateStudentForm(List<String> genders, List<String> nationalities);
	public CreateCourseResponse renderCreateCourseForm(List<String> schools);
	public CreateIndexResponse renderCreateIndexForm(String course);
	public CreateLessonResponse renderCreateLessonForm(String index, List<String> lessonType, List<String> days);
	public AccessPeriodResponse renderAccessPeriodForm(String curAccessPeriod);
	public CourseManagementResponse renderCourseManagementForm(List<String> courses);
	public IndexManagementResponse renderIndexManagementForm(String courseCode, List<String> indexes);
	
	public String getText(String title, String description);
	public Integer getInt(String title, String description);
	public void renderDialog(String title, String msg);
	public void renderStudentList(String title, List<String[]> students);
}

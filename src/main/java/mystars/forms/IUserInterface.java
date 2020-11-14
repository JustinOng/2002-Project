package mystars.forms;

import java.util.List;

public interface IUserInterface {
	public LoginResponse renderLoginForm();
	public StudentMenuResponse renderStudentMenuForm(List<String> courses);
	public AdminMenuResponse renderAdminMenuForm();
	public IndexSwopResponse renderIndexSwopForm();
	public TextResponse renderItemSelectorForm(String title, List<String> items);
	public CreateStudentResponse renderCreateStudentForm(List<String> genders, List<String> nationalities);
	public CreateCourseResponse renderCreateCourseForm();
	public CreateIndexResponse renderCreateIndexForm();
	public AccessPeriodResponse renderAccessPeriodForm(String curAccessPeriod);
	
	public String getText(String title, String description);
	public Integer getInt(String title, String description);
	public void renderDialog(String title, String msg);
}

package mystars.forms;

import java.util.List;

public interface IUserInterface {
	public LoginResponse renderLoginForm(String msg);
	public StudentMenuResponse renderStudentMenuForm(List<String> courses);
	public IndexSwopResponse renderIndexSwopForm();
	public TextResponse renderItemSelectorForm(String title, List<String> items);
	public CreateStudentResponse renderCreateStudentForm();
	public CreateCourseResponse renderCreateCourseForm();
	public CreateIndexResponse renderCreateIndexForm();
	
	public String getText(String title, String description);
	public Integer getInt(String title, String description);
	public void renderDialog(String title, String msg);
}

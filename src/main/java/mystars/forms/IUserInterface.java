package mystars.forms;

import java.util.List;

public interface IUserInterface {
	public LoginResponse renderLoginForm(String msg);
	public TextResponse renderStudentMenuForm(List<String> courses);
	public IndexSwopResponse renderIndexSwopForm();
	public TextResponse renderItemSelectorForm(String title, List<String> items);
	public TextResponse renderTextInput(String title, String description);
	public CreateStudentResponse renderCreateStudentForm();
	public CreateCourseResponse renderCreateCourseForm();
	public CreateIndexResponse renderCreateIndexForm();
}

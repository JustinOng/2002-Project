package mystars.forms;

import java.util.List;

public interface IUserInterface {
	public LoginResponse renderLoginForm();
	public TextResponse renderStudentMenuForm(List<String> courses);
	public TextResponse renderItemSelectorForm(String itemType, List<String> items);
	public TextResponse renderTextInput(String title, String description);
}

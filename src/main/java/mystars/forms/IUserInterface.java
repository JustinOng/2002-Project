package mystars.forms;

import java.util.List;

public interface IUserInterface {
	public LoginResponse renderLoginForm();
	public SelectorResponse renderStudentMenuForm(List<String> courses);
	public SelectorResponse renderItemSelectorForm(String itemType, List<String> items);
}

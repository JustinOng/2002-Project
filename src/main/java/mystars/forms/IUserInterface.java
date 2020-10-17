package mystars.forms;

import java.util.List;

public interface IUserInterface {
	public LoginResponse renderLoginForm();
	public SelectorResponse renderItemSelectorForm(String itemType, List<String> items);
}

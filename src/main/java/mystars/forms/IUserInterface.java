package mystars.forms;

import java.util.List;

public interface IUserInterface {
	public void renderLoginForm();
	public void renderItemSelectorForm(String itemType, List<String> items);
}

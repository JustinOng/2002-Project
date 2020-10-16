package mystars;

import java.io.IOException;
import java.util.Arrays;

import mystars.forms.IUserInterfaceObserver;
import mystars.forms.cgui.ConsoleGraphicUserInterface;

public class MySTARS implements IUserInterfaceObserver {
	private ConsoleGraphicUserInterface ui = new ConsoleGraphicUserInterface(this);

	public MySTARS() throws IOException {
		ui.renderLoginForm();
	}

	public void onLogin(String username, String password, String loginType) {
		System.out.println(String.format("%s %s %s", username, password, loginType));
		ui.renderItemSelectorForm("Select Course", Arrays.asList("Course A", "Course B"));
	}
	
	public void onItemSelect(String itemType, String item) {
		System.out.println(String.format("%s %s", itemType, item));
	}
}

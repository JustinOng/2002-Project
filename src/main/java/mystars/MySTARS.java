package mystars;

import java.io.IOException;

import mystars.forms.IUserInterfaceObserver;
import mystars.forms.cgui.ConsoleGraphicUserInterface;

public class MySTARS implements IUserInterfaceObserver {
	public MySTARS() throws IOException {
		ConsoleGraphicUserInterface ui = new ConsoleGraphicUserInterface(this);
		ui.renderLoginForm();
	}

	public void onLogin(String username, String password, String loginType) {
		System.out.println(String.format("%s %s %s", username, password, loginType));
	}
}

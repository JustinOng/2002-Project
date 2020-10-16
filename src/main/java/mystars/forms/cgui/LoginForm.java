package mystars.forms.cgui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import mystars.forms.IUserInterfaceObserver;
import mystars.forms.Observer;

import java.util.Arrays;

public class LoginForm extends Observer {
	private AbstractWindow window = new BasicWindow();

	public LoginForm() {
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(2));

		final Label status = new Label("");
		panel.addComponent(status, GridLayout.createHorizontallyFilledLayoutData(2));

		panel.addComponent(new Label("Username:"));
		final TextBox usernameInput = new TextBox().addTo(panel);

		panel.addComponent(new Label("Password:"));
		final TextBox passwordInput = new TextBox().addTo(panel);
		passwordInput.setMask('*');

		panel.addComponent(new Label("I am a:"));
		final ComboBox<String> loginTypeInput = new ComboBox<String>(Arrays.asList("Student", "Admin"), 0);
		panel.addComponent(loginTypeInput, GridLayout.createHorizontallyFilledLayoutData(2));

		panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));
		new Button("Login", new Runnable() {
			public void run() {
				String username = usernameInput.getText();
				String password = passwordInput.getText();
				String loginType = loginTypeInput.getSelectedItem();

				pushObserver(username, password, loginType);
			}
		}).addTo(panel);

		window.setComponent(panel);

		KeyStrokeListener listener = new KeyStrokeListener();
		window.setTitle("Login");
		window.addWindowListener(listener);
		window.setHints(Arrays.asList(Window.Hint.CENTERED));
	}

	public AbstractWindow getWindow() {
		return window;
	}

	public void pushObserver(String username, String password, String loginType) {
		for (IUserInterfaceObserver observer : this.observers) {
			observer.onLogin(username, password, loginType);
		}
	}
}

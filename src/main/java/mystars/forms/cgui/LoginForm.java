package mystars.forms.cgui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import mystars.forms.*;

import java.util.Arrays;

public class LoginForm {
	private LoginResponse response;
	
	public LoginResponse getResponse(MultiWindowTextGUI gui, String msg) {
		final AbstractWindow window = new BasicWindow();
		
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(2));

		final Label status = new Label(msg);
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

				response = new LoginResponse(username, password, loginType);
				window.close();
			}
		}).addTo(panel);

		window.setComponent(panel);

		KeyStrokeListener listener = new KeyStrokeListener();
		window.setTitle("Login");
		window.addWindowListener(listener);
		window.setHints(Arrays.asList(Window.Hint.CENTERED));
		
		gui.addWindowAndWait(window);
		
		return response;
	}
}

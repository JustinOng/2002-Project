package mystars.forms.cgui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import mystars.forms.*;

import java.util.Arrays;

public class LoginForm {
	private LoginResponse response;
	
	public LoginResponse getResponse(MultiWindowTextGUI gui) {
		final AbstractWindow window = new BasicWindow();
		
		response = null;
		
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(2));

		panel.addComponent(new Label("Username:"));
		final TextBox usernameInput = new TextBox().addTo(panel);

		panel.addComponent(new Label("Password:"));
		final TextBox passwordInput = new TextBox().addTo(panel);
		passwordInput.setMask('*');

		panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));
		
		new Button("Login", new Runnable() {
			public void run() {
				String username = usernameInput.getText();
				String password = passwordInput.getText();

				response = new LoginResponse(username, password);
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

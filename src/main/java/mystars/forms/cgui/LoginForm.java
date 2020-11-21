package mystars.forms.cgui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import mystars.forms.*;

import java.util.Arrays;

/**
 * <h1>Class: LoginForm</h1>
 * 
 * This class manages the user interface for the user login form.
 */
public class LoginForm {
	/**
	 * Object used to contain response of this form. Class attribute to avoid error
	 * due to assignment from runnable
	 */
	private LoginResponse response;

	/**
	 * Displays login form
	 * 
	 * @param gui The graphical user interface object.
	 * @return {@code LoginResponse} containing user input, or {@code null} if the
	 *         form is closed without any input
	 */
	public LoginResponse getResponse(MultiWindowTextGUI gui) {
		final AbstractWindow window = new BasicWindow();

		response = null;

		// Create new Jpanel object and set the layout as a grid.
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(2));

		// Insert textboxes, labels and buttons as required.
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

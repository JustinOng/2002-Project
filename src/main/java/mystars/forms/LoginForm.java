package mystars.forms;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.TerminalSize;

import java.util.Arrays;

public class LoginForm {
	public static BasicWindow getWindow() {
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(2));
		
		final Label status = new Label("");
		panel.addComponent(status, GridLayout.createHorizontallyFilledLayoutData(2));

		panel.addComponent(new Label("Username:"));
		final TextBox usernameInput = new TextBox().addTo(panel);
		
		panel.addComponent(new Label("Password:"));
		final TextBox passwordInput = new TextBox().addTo(panel);
		passwordInput.setMask('*');
		
		panel.addComponent(new Label("I am a:"), GridLayout.createHorizontallyFilledLayoutData(2));
		RadioBoxList<String> loginType = new RadioBoxList<String>();
		loginType.addItem("Student");
		loginType.addItem("Admin");
		loginType.setCheckedItemIndex(0);
		panel.addComponent(loginType, GridLayout.createHorizontallyFilledLayoutData(2));
		
		panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));
		new Button("Login", new Runnable() {
			public void run() {
				String username = usernameInput.getText();
				String password = passwordInput.getText();
				
				status.setText("Login failed");
				
				System.out.println(String.format("%s %s",  username, password));
			}
		}).addTo(panel);
		
		BasicWindow window = new BasicWindow();
		window.setComponent(panel);
		
		KeyStrokeListener listener = new KeyStrokeListener();
		window.setTitle("Login");
		window.addWindowListener(listener);
    	window.setHints(Arrays.asList(Window.Hint.CENTERED));
		
		return window;
	}
}

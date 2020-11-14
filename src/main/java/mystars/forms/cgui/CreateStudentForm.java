package mystars.forms.cgui;

import java.util.Arrays;
import java.util.List;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import mystars.forms.*;

public class CreateStudentForm {
	private CreateStudentResponse response;

	public CreateStudentResponse getResponse(MultiWindowTextGUI gui, List<String> genders, List<String> nationalities) {
		final AbstractWindow window = new BasicWindow();

		response = null;

		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(2));

		final Label status = new Label("");
		panel.addComponent(status, GridLayout.createHorizontallyFilledLayoutData(2));

		panel.addComponent(new Label("Username:"));
		final TextBox usernameInput = new TextBox().addTo(panel);

		panel.addComponent(new Label("Password:"));
		final TextBox passwordInput = new TextBox().addTo(panel);

		panel.addComponent(new Label("Name:"));
		final TextBox nameInput = new TextBox().addTo(panel);

		panel.addComponent(new Label("Matric. No.:"));
		final TextBox matricNoInput = new TextBox().addTo(panel);

		panel.addComponent(new Label("Gender:"));
		final ComboBox<String> genderBox = new ComboBox<String>();
		for (String s : genders) {
			genderBox.addItem(s);
		}
		panel.addComponent(genderBox);

		panel.addComponent(new Label("Nationality:"));
		final ComboBox<String> nationalityBox = new ComboBox<String>();
		for (String s : nationalities) {
			nationalityBox.addItem(s);
		}
		panel.addComponent(nationalityBox);

		panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));

		new Button("Create", new Runnable() {
			public void run() {
				response = new CreateStudentResponse(usernameInput.getText(), passwordInput.getText(),
						nameInput.getText(), matricNoInput.getText(), genderBox.getSelectedItem(),
						nationalityBox.getSelectedItem());
				window.close();
			}
		}).addTo(panel);

		window.setComponent(panel);

		KeyStrokeListener listener = new KeyStrokeListener();
		window.setTitle("Create New Student");
		window.addWindowListener(listener);
		window.setHints(Arrays.asList(Window.Hint.CENTERED));

		gui.addWindowAndWait(window);

		return response;
	}
}

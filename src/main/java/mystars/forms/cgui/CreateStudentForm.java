package mystars.forms.cgui;

import java.util.Arrays;
import java.util.List;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import mystars.forms.*;

/**
 * <h1>Class: CreateStudentForm</h1>
 * 
 * This manages the user interface for the creation of new student form.
 */
public class CreateStudentForm {
	/**
	 * The response object for the creation of new student form.
	 */
	private CreateStudentResponse response;

	/**
	 * Sets the parameters for the graphical user interface administrators 
	 * will use when creating new students.
	 * 
	 * @param gui			The graphical user interface object.
	 * @param genders		The gender of the student.
	 * @param nationalities	The nationality of the student.
	 * @return				The creation of new student form response object.
	 */
	public CreateStudentResponse getResponse(MultiWindowTextGUI gui, List<String> genders, List<String> nationalities) {
		final AbstractWindow window = new BasicWindow();

		response = null;

		// Create new Jpanel object and set the layout as a grid.
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(2));

		// Insert textboxes, labels and buttons as required.
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
		final ComboBox<String> genderBox = new ComboBox<String>(genders);
		panel.addComponent(genderBox);

		panel.addComponent(new Label("Nationality:"));
		final ComboBox<String> nationalityBox = new ComboBox<String>(nationalities);
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

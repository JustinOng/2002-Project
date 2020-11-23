package mystars.forms.cgui;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;

import mystars.forms.*;

/**
 * <h1>Class: CreateStudentForm</h1>
 * 
 * This manages the user interface for the creation of new student form.
 */
public class CreateStudentForm {
	/**
	 * Object used to contain response of this form. Class attribute to avoid error
	 * due to assignment from runnable
	 */
	private CreateStudentResponse response;

	/**
	 * Displays form requesting user input for creating a student
	 * 
	 * @param gui           The graphical user interface object.
	 * @param genders       The list of genders that a student can assume
	 * @param nationalities The list of nationalities that a student can assume
	 * @return User input to be used to create a new Student, or {@code null} if the
	 *         form is cancelled/closed without any input
	 */
	public CreateStudentResponse getResponse(MultiWindowTextGUI gui, List<String> genders, List<String> nationalities) {
		final AbstractWindow window = new BasicWindow();

		response = null;

		// Create new panel object and set the layout as a grid.
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(2));

		// Insert textboxes, labels and buttons as required.
		final Label status = new Label("");
		panel.addComponent(status, GridLayout.createHorizontallyFilledLayoutData(2));

		panel.addComponent(new Label("Username:"));
		final TextBox usernameInput = new TextBox().addTo(panel);
		usernameInput.setValidationPattern(Pattern.compile("^[a-zA-Z0-9]+$"));

		panel.addComponent(new Label("Password:"));
		final TextBox passwordInput = new TextBox().addTo(panel);

		panel.addComponent(new Label("Name:"));
		final TextBox nameInput = new TextBox().addTo(panel);
		nameInput.setValidationPattern(Pattern.compile("^[a-zA-Z0-9, ]+$"));

		panel.addComponent(new Label("Email:"));
		final TextBox emailInput = new TextBox().addTo(panel);
		emailInput.setValidationPattern(Pattern.compile("^[a-zA-Z0-9\\.\\+@]+$"));

		panel.addComponent(new Label("Matric. No.:"));
		final TextBox matricNoInput = new TextBox().addTo(panel);
		matricNoInput.setValidationPattern(Pattern.compile("^[A-Z0-9]+$"));

		panel.addComponent(new Label("Gender:"));
		final ComboBox<String> genderBox = new ComboBox<String>(genders);
		panel.addComponent(genderBox);

		panel.addComponent(new Label("Nationality:"));
		final ComboBox<String> nationalityBox = new ComboBox<String>(nationalities);
		panel.addComponent(nationalityBox);

		panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));

		new Button("Create", new Runnable() {
			public void run() {
				String username = usernameInput.getText();
				String password = passwordInput.getText();
				String name = nameInput.getText();
				String email = emailInput.getText();
				String matricNo = matricNoInput.getText();

				if (username.isBlank() || password.isBlank() || name.isBlank() || email.isBlank()
						|| matricNo.isBlank()) {
					MessageDialog.showMessageDialog(gui, "Error", "All fields must be filled out");
					return;
				}

				try {
					InternetAddress.parse(email, true);
					if (!email.matches("^[a-zA-Z0-9+\\.]+@[a-zA-Z0-9+\\.]+$")) {
						throw new AddressException();
					}
				} catch (AddressException e) {
					MessageDialog.showMessageDialog(gui, "Error", "Please enter a valid email address");
					return;
				}

				// https://github.com/idf/idf.github.io-deprecated/blob/master/_posts/2014-03-17-the-secret-behind-ntu-matriculation-numbers.md
				if (!matricNo.matches("^[U|G|N|P|D][0-9]{7}[ABCDEFGHJKL]$")) {
					MessageDialog.showMessageDialog(gui, "Error", "Please enter a valid matriculation number");
					return;
				}

				response = new CreateStudentResponse(username, password, name, email, matricNo,
						genderBox.getSelectedItem(), nationalityBox.getSelectedItem());
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

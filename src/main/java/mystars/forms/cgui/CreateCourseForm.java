package mystars.forms.cgui;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;

import mystars.forms.*;

/**
 * <h1>Class: CreateCourseForm</h1>
 * 
 * This class manages the user interface for the course creation form.
 */
public class CreateCourseForm {
	/**
	 * Object used to contain response of this form. Class attribute to avoid error
	 * due to assignment from runnable
	 */
	private CreateCourseResponse response;

	/**
	 * Displays form requesting user input for creating a course
	 * 
	 * @param gui     The graphical user interface object.
	 * @param schools The list of schools that a course can belong to
	 * @return User input to be used to create a new Course, or {@code null} if the
	 *         form is cancelled/closed without any input
	 */
	public CreateCourseResponse getResponse(MultiWindowTextGUI gui, List<String> schools) {
		final AbstractWindow window = new BasicWindow();

		response = null;

		// Create new panel object and set the layout as a grid.
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(2));

		// Insert textboxes, labels and buttons as required.
		panel.addComponent(new Label("Code:"));
		final TextBox codeInput = new TextBox().addTo(panel);
		codeInput.setValidationPattern(Pattern.compile("^[A-Z0-9]+$"));

		panel.addComponent(new Label("Name:"));
		final TextBox nameInput = new TextBox().addTo(panel);
		nameInput.setValidationPattern(Pattern.compile("^[a-zA-Z0-9 ]+$"));

		panel.addComponent(new Label("School:"));
		final ComboBox<String> schoolComboBox = new ComboBox<String>(schools).addTo(panel);

		panel.addComponent(new Label("AUs:"));
		final TextBox auInput = new TextBox().addTo(panel);
		auInput.setValidationPattern(Pattern.compile("^[0-9]+$"));

		panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));

		new Button("Create", new Runnable() {
			public void run() {
				String code = codeInput.getText();
				String name = nameInput.getText();

				if (code.isBlank()) {
					MessageDialog.showMessageDialog(gui, "Error", "Code cannot be blank!");
					return;
				}
				
				if (!code.matches("^[A-Z]+[0-9]+$")) {
					MessageDialog.showMessageDialog(gui, "Error", "Invalid course code format");
					return;
				}

				if (name.isBlank()) {
					MessageDialog.showMessageDialog(gui, "Error", "Name cannot be blank!");
					return;
				}

				int au;
				try {
					au = Integer.parseInt(auInput.getText());
				} catch (NumberFormatException e) {
					MessageDialog.showMessageDialog(gui, "Error", "Error parsing number");
					return;
				}

				response = new CreateCourseResponse(code, name, schoolComboBox.getText(), au);
				window.close();
			}
		}).addTo(panel);

		window.setComponent(panel);

		KeyStrokeListener listener = new KeyStrokeListener();
		window.setTitle("Create Course");
		window.addWindowListener(listener);
		window.setHints(Arrays.asList(Window.Hint.CENTERED));

		gui.addWindowAndWait(window);

		return response;
	}
}

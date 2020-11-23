package mystars.forms.cgui;

import java.util.Arrays;
import java.util.regex.Pattern;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;

import mystars.forms.*;

/**
 * <h1>Class: CreateIndexForm</h1>
 * 
 * This class manages the user interface for the course index creation form.
 */
public class CreateIndexForm {
	/**
	 * Object used to contain response of this form. Class attribute to avoid error
	 * due to assignment from runnable
	 */
	private CreateIndexResponse response;

	/**
	 * Displays form requesting user input for creating a course index
	 * 
	 * @param gui    The graphical user interface object.
	 * @param course Parent course name to be displayed on the form
	 * @return User input to be used to create a new Index, or {@code null} if the
	 *         form is cancelled/closed without any input
	 */
	public CreateIndexResponse getResponse(MultiWindowTextGUI gui, String course) {
		final AbstractWindow window = new BasicWindow();

		response = null;

		// Create new panel object and set the layout as a grid.
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(2));

		// Insert textboxes, labels and buttons as required.
		panel.addComponent(new Label("Index Number:"));
		final TextBox numberInput = new TextBox().addTo(panel);
		numberInput.setValidationPattern(Pattern.compile("^[0-9]+$"));

		panel.addComponent(new Label("Maximum Enrolled:"));
		final TextBox maxEnrolledInput = new TextBox().addTo(panel);
		maxEnrolledInput.setValidationPattern(Pattern.compile("^[0-9]+$"));

		panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));

		new Button("Create", new Runnable() {
			public void run() {
				int indexNumber, maxEnrolled;

				try {
					indexNumber = Integer.parseInt(numberInput.getText());
					maxEnrolled = Integer.parseInt(maxEnrolledInput.getText());
				} catch (NumberFormatException e) {
					MessageDialog.showMessageDialog(gui, "Error", "Error parsing number");
					return;
				}

				response = new CreateIndexResponse(indexNumber, maxEnrolled);
				window.close();
			}
		}).addTo(panel);

		window.setComponent(panel);

		KeyStrokeListener listener = new KeyStrokeListener();
		window.setTitle("Create Index on " + course);
		window.addWindowListener(listener);
		window.setHints(Arrays.asList(Window.Hint.CENTERED));

		gui.addWindowAndWait(window);

		return response;
	}
}

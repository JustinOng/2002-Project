package mystars.forms.cgui;

import java.util.Arrays;
import java.util.regex.Pattern;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import mystars.forms.*;

/**
 * <h1>Class: CreateIndexForm</h1>
 * 
 * This class manages the user interface for the course index creation form.
 */
public class CreateIndexForm {
	/**
	 * The response object for for the course index creation form.
	 */
	private CreateIndexResponse response;
	
	/**
	 * Sets the parameters for the graphical user interface administrators 
	 * will use when creating course indexes.
	 * 
	 * @param gui		The graphical user interface object.
	 * @param course	The course name.
	 * @return			The course index creation form response object.
	 */
	public CreateIndexResponse getResponse(MultiWindowTextGUI gui, String course) {
		final AbstractWindow window = new BasicWindow();
		
		response = null;

		// Create new Jpanel object and set the layout as a grid.
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(2));

		// Insert textboxes, labels and buttons as required.
		panel.addComponent(new Label("Number:"));
		final TextBox numberInput = new TextBox().addTo(panel);
		numberInput.setValidationPattern(Pattern.compile("^[0-9]+$"));

		panel.addComponent(new Label("Maximum Enrolled:"));
		final TextBox maxEnrolledInput = new TextBox().addTo(panel);
		maxEnrolledInput.setValidationPattern(Pattern.compile("^[0-9]+$"));

		panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));

		new Button("Create", new Runnable() {
			public void run() {
				response = new CreateIndexResponse(Integer.parseInt(numberInput.getText()), Integer.parseInt(maxEnrolledInput.getText()));
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

package mystars.forms.cgui;

import java.util.Arrays;
import java.util.regex.Pattern;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;

import mystars.forms.*;

/**
 * <h1>Class: IndexSwopForm</h1>
 * 
 * This class manages the user interface for the form for swopping of course
 * indexes between two students.
 */
public class IndexSwopForm {
	/**
	 * Object used to contain response of this form. Class attribute to avoid error
	 * due to assignment from runnable
	 */
	private IndexSwopResponse response;

	/**
	 * Displays form requesting user input for swopping indexes
	 * 
	 * @param gui The graphical user interface object.
	 * @return User input, or {@code null} if the form is cancelled/closed without
	 *         any input
	 */
	public IndexSwopResponse getResponse(MultiWindowTextGUI gui) {
		final AbstractWindow window = new BasicWindow();

		response = null;

		// Create new panel object and set the layout as a grid.
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(2));

		// Insert textboxes, labels and buttons as required.
		panel.addComponent(new Label("Your Index Number:"));
		final TextBox indexAInput = new TextBox().addTo(panel);
		indexAInput.setValidationPattern(Pattern.compile("^[0-9]+$"));

		panel.addComponent(new Label("Peer's Index Number:"));
		final TextBox indexBInput = new TextBox().addTo(panel);
		indexBInput.setValidationPattern(Pattern.compile("^[0-9]+$"));

		panel.addComponent(new Label("Peer's Username:"));
		final TextBox usernameInput = new TextBox().addTo(panel);

		panel.addComponent(new Label("Peer's Password:"));
		final TextBox passwordInput = new TextBox().addTo(panel);
		passwordInput.setMask('*');

		panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));

		new Button("Swop", new Runnable() {
			public void run() {
				int indexA, indexB;
				try {
					indexA = Integer.parseInt(indexAInput.getText());
					indexB = Integer.parseInt(indexBInput.getText());
				} catch (NumberFormatException e) {
					MessageDialog.showMessageDialog(gui, "Error", "Error parsing number");
					return;
				}

				response = new IndexSwopResponse(indexA, indexB, usernameInput.getText(), passwordInput.getText());
				window.close();
			}
		}).addTo(panel);

		window.setComponent(panel);

		KeyStrokeListener listener = new KeyStrokeListener();
		window.setTitle("Swop Index");
		window.addWindowListener(listener);
		window.setHints(Arrays.asList(Window.Hint.CENTERED));

		gui.addWindowAndWait(window);

		return response;
	}
}

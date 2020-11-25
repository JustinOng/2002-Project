package mystars.forms.cgui;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;

import mystars.forms.*;

/** 
 * This class manages user interface for the access period form.
 */
public class AccessPeriodForm {
	/**
	 * Object used to contain response of this form. Class attribute to avoid error
	 * due to assignment from runnable
	 */
	private static AccessPeriodResponse response;

	/**
	 * Display a form requesting input for the access period
	 * 
	 * @param gui             The graphical user interface object to display the UI
	 *                        on
	 * @param curAccessPeriod The current access period. Displayed on the form
	 * @return User input, or {@code null} if the form is cancelled/closed without
	 *         any input
	 */
	public static AccessPeriodResponse getResponse(MultiWindowTextGUI gui, String curAccessPeriod) {
		final AbstractWindow window = new BasicWindow();

		response = null;

		// Create new panel object and set the layout as a grid.
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(2));

		// Insert textboxes, labels and buttons as required.
		if (curAccessPeriod == null) {
			final Label status = new Label("Enter datetime eg 2020-01-01T08:00:00");
			panel.addComponent(status, GridLayout.createHorizontallyFilledLayoutData(2));
		} else {
			final Label status = new Label("Current access period: " + curAccessPeriod);
			status.setLabelWidth(1);
			panel.addComponent(status, GridLayout.createHorizontallyFilledLayoutData(2));
		}

		panel.addComponent(new Label("Start:"));
		final TextBox startInput = new TextBox(new TerminalSize(20, 1)).addTo(panel);

		panel.addComponent(new Label("End:"));
		final TextBox endInput = new TextBox(new TerminalSize(20, 1)).addTo(panel);

		panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));

		new Button("Save", new Runnable() {
			public void run() {
				try {
					LocalDateTime start = LocalDateTime.parse(startInput.getText());
					LocalDateTime end = LocalDateTime.parse(endInput.getText());

					if (start.isAfter(end) || start.isEqual(end)) {
						MessageDialog.showMessageDialog(gui, "Error", "Start time must be before end time");
						return;
					}

					response = new AccessPeriodResponse(start, end);
					window.close();
				} catch (DateTimeParseException e) {
					MessageDialog.showMessageDialog(gui, "Error", "Invalid date");
				}
			}
		}).addTo(panel);

		window.setComponent(panel);

		KeyStrokeListener listener = new KeyStrokeListener();
		window.setTitle("Set Student Access Period");
		window.addWindowListener(listener);
		window.setHints(Arrays.asList(Window.Hint.CENTERED));

		gui.addWindowAndWait(window);

		return response;
	}
}

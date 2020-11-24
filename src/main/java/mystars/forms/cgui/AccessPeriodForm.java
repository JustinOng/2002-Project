package mystars.forms.cgui;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;

import mystars.forms.*;

/**
 * <h1>Class: AccessPeriodForm</h1>
 * 
 * This class manages user interface for the access period form.
 */
public class AccessPeriodForm {
	/**
	 * Object used to contain response of this form. Class attribute to avoid error
	 * due to assignment from runnable
	 */
	private static AccessPeriodResponse response;

	/**
	 * Sets the parameters for the graphical user interface administrators will use
	 * when setting or modifying the access period starting and ending date and
	 * time. Checks for invalid date range.
	 * 
	 * @param gui             The graphical user interface object.
	 * @param curAccessPeriod The current access period.
	 * @return The access period response object.
	 */
	public static AccessPeriodResponse getResponse(MultiWindowTextGUI gui, String curAccessPeriod) {
		final AbstractWindow window = new BasicWindow();

		response = null;

		// Create new Jpanel object and set the layout as a grid.
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

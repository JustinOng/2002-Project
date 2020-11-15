package mystars.forms.cgui;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;

import mystars.forms.*;

public class AccessPeriodForm {
	private AccessPeriodResponse response;

	public AccessPeriodResponse getResponse(MultiWindowTextGUI gui, String curAccessPeriod) {
		final AbstractWindow window = new BasicWindow();

		response = null;

		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(2));

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
					response = new AccessPeriodResponse(LocalDateTime.parse(startInput.getText()),
							LocalDateTime.parse(endInput.getText()));
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

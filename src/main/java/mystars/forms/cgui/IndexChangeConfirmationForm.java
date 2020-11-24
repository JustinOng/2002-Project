package mystars.forms.cgui;

import java.util.Arrays;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;

import mystars.entities.*;

/**
 * <h1>Class: IndexChangeConfirmationForm</h1>
 * 
 * This class manages the user interface for confirming an index change.
 */
public class IndexChangeConfirmationForm {
	/**
	 * Object used to contain response of this form. Class attribute to avoid error
	 * due to assignment from runnable
	 */
	private static boolean response;

	/**
	 * Display detailed information about two indexes and request for the user to
	 * confirm an action
	 * 
	 * @param gui         The graphical user interface object.
	 * @param title       Title of the form
	 * @param description Description to be shown on the form
	 * @param labelA      Text to label {@code indexA} with
	 * @param indexA      First index to display
	 * @param labelB      Text to label {@code indexB} with
	 * @param indexB      Second index to display
	 * @return {@code true} if the user confirmed the action, or {@code false}
	 *         otherwise
	 */
	public static boolean confirm(MultiWindowTextGUI gui, String title, String description, String labelA, Index indexA,
			String labelB, Index indexB) {
		final AbstractWindow window = new BasicWindow();

		response = false;

		// Create new panel object and set the layout as a grid.
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(2));

		panel.addComponent(new Label(description), GridLayout.createHorizontallyFilledLayoutData(2));

		panel.addComponent(new Label(labelA));
		panel.addComponent(new Label(labelB));

		Table<String> tableA = new Table<String>("Class Type", "Group", "Day", "Time", "Venue");
		for (Lesson lesson : indexA.getLessons()) {
			tableA.getTableModel().addRow(new String[] { lesson.getLessonType().toString(), lesson.getGroupNo(),
					lesson.getDay().toString(), lesson.getTimeString().toString(), lesson.getLocation() });
		}
		panel.addComponent(tableA);

		Table<String> tableB = new Table<String>("Class Type", "Group", "Day", "Time", "Venue");
		for (Lesson lesson : indexB.getLessons()) {
			tableB.getTableModel().addRow(new String[] { lesson.getLessonType().toString(), lesson.getGroupNo(),
					lesson.getDay().toString(), lesson.getTimeString().toString(), lesson.getLocation() });
		}
		panel.addComponent(tableB);

		new Button("Cancel", new Runnable() {
			public void run() {
				response = false;
				window.close();
			}
		}).addTo(panel);

		new Button("Confirm", new Runnable() {
			public void run() {
				response = true;
				window.close();
			}
		}).addTo(panel);

		window.setComponent(panel);

		window.setTitle(title);
		KeyStrokeListener listener = new KeyStrokeListener();
		window.addWindowListener(listener);
		window.setHints(Arrays.asList(Window.Hint.CENTERED));

		gui.addWindowAndWait(window);

		return response;
	}
}

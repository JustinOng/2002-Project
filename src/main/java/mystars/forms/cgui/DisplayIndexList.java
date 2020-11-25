package mystars.forms.cgui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;

import mystars.entities.*;

import java.util.Arrays;
import java.util.List;

/**
 * This class displays a list of lessons for the given index(s)
 */
public class DisplayIndexList {
	/**
	 * Displays a list of indexes with the given title
	 * 
	 * @param gui     The graphical user interface object to display the UI on
	 * @param title   The title of the form.
	 * @param indexes List of indexes to display
	 */
	public static void show(MultiWindowTextGUI gui, String title, List<Index> indexes) {
		final AbstractWindow window = new BasicWindow();

		// Create new panel object and set the layout as a grid.
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));

		for (Index index : indexes) {
			panel.addComponent(new Label(index.toString()));

			Table<String> table = new Table<String>("Type", "Group", "Day", "Time", "Venue");
			for (Lesson lesson : index.getLessons()) {
				table.getTableModel().addRow(new String[] { lesson.getLessonType().toString(), lesson.getGroupNo(),
						lesson.getDay().toString(), lesson.getTimeString().toString(), lesson.getLocation() });
			}
			panel.addComponent(table);
		}

		panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));

		new Button("Back", new Runnable() {
			public void run() {
				window.close();
			}
		}).addTo(panel);

		window.setComponent(panel);

		KeyStrokeListener listener = new KeyStrokeListener();
		window.setTitle(title);
		window.addWindowListener(listener);
		window.setHints(Arrays.asList(Window.Hint.CENTERED));

		gui.addWindowAndWait(window);

		return;
	}
}

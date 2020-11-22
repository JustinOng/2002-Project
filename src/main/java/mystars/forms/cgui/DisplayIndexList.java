package mystars.forms.cgui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <h1>Class: DisplayStudentList</h1>
 * 
 * This class displays a list of lessons for the given index(s)
 */
public class DisplayIndexList {
	/**
	 * @param gui     The graphical user interface object.
	 * @param title   The title of the form.
	 * @param indexes Map of index number to index info, where index info is a list
	 *                of string arrays containing {"Class Type", "Group", "Day",
	 *                "Time", "Venue"}
	 */
	public static void show(MultiWindowTextGUI gui, String title, Map<String, List<String[]>> indexes) {
		final AbstractWindow window = new BasicWindow();

		// Create new panel object and set the layout as a grid.
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));

		for (Map.Entry<String, List<String[]>> entry : indexes.entrySet()) {
			String indexLabel = entry.getKey();
			List<String[]> lessons = entry.getValue();
			
			panel.addComponent(new Label(indexLabel));

			Table<String> table = new Table<String>("Type", "Group", "Day", "Time", "Venue");
			for (String[] data : lessons) {
				table.getTableModel().addRow(data);
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

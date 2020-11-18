package mystars.forms.cgui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;

import java.util.Arrays;
import java.util.List;

/**
 * <h1>Class: DisplayStudentList</h1>
 * 
 * This manages the user interface for displaying the list of students.
 */
public class DisplayStudentList {
	/**
	 * Sets the parameters for the graphical user interface administrators 
	 * will use when displaying the list of all the students.
	 * 
	 * @param gui		The graphical user interface object.
	 * @param title		The title of the form.
	 * @param students	The list of students.
	 */
	public static void show(MultiWindowTextGUI gui, String title, List<String[]> students) {
		final AbstractWindow window = new BasicWindow();

		// Create new Jpanel object and set the layout as a grid.
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));

		// Insert textboxes, labels and buttons as required.
		if (students.size() > 0) {
			Table<String> table = new Table<String>("Name", "Gender", "Nationality");
			for (String[] data : students) {
				table.getTableModel().addRow(data);
			}
			panel.addComponent(table);
		} else {
			panel.addComponent(new Label("No students registered"));
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

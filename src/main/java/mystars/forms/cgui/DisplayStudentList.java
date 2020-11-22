package mystars.forms.cgui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;

import mystars.entities.Student;

import java.util.Arrays;
import java.util.List;

/**
 * <h1>Class: DisplayStudentList</h1>
 * 
 * This manages the user interface for displaying the list of students.
 */
public class DisplayStudentList {
	/**
	 * @param gui      The graphical user interface object.
	 * @param title    The title of the form.
	 * @param students The list of students. List of string arrays, ie { {"name",
	 *                 "gender", "nationality}, ...}
	 */
	public static void show(MultiWindowTextGUI gui, String title, List<Student> students) {
		final AbstractWindow window = new BasicWindow();

		// Create new panel object and set the layout as a grid.
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));

		if (students.size() > 0) {
			Table<String> table = new Table<String>("Name", "Gender", "Nationality");
			for (Student s : students) {
				table.getTableModel()
						.addRow(new String[] { s.getName(), s.getGender().toString(), s.getNationality().toString() });
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

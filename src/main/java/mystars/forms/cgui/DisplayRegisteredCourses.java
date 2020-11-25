package mystars.forms.cgui;

import java.util.Arrays;
import java.util.List;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;

import mystars.entities.*;

/** 
 * This class displays full information about a course, including lessons.
 */
public class DisplayRegisteredCourses {
	/**
	 * 
	 * Display a list of courses and their individual lessons
	 * 
	 * @param gui   The graphical user interface object to display the UI on
	 * @param title Title of the form
	 * @param regs  List of Registrations to display
	 */
	public static void display(MultiWindowTextGUI gui, String title, List<Registration> regs) {
		final AbstractWindow window = new BasicWindow();

		// Create new panel object and set the layout as a grid.
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));

		Table<String> table = new Table<String>("Course", "Title", "AU", "Status", "Index No.", "Class Type", "Group",
				"Day", "Time", "Venue");

		for (Registration reg : regs) {
			Index index = reg.getIndex();
			Course course = index.getCourse();

			boolean firstRow = true;
			for (Lesson lesson : index.getLessons()) {
				if (firstRow) {
					table.getTableModel()
							.addRow(new String[] { course.getCourseCode(), course.getName(),
									Integer.toString(course.getAu()), reg.getStatus().toString(),
									Integer.toString(index.getIndexNo()), lesson.getLessonType().toString(),
									lesson.getGroupNo(), lesson.getDay().toString(), lesson.getTimeString(),
									lesson.getLocation() });
				} else {
					table.getTableModel()
							.addRow(new String[] { "", "", "", "", "", lesson.getLessonType().toString(),
									lesson.getGroupNo(), lesson.getDay().toString(), lesson.getTimeString(),
									lesson.getLocation() });
				}

				firstRow = false;
			}
		}

		panel.addComponent(table);

		new Button("Back", new Runnable() {
			public void run() {
				window.close();
			}
		}).addTo(panel);

		window.setComponent(panel);

		window.setTitle(title);
		KeyStrokeListener listener = new KeyStrokeListener();
		window.addWindowListener(listener);
		window.setHints(Arrays.asList(Window.Hint.CENTERED));

		gui.addWindowAndWait(window);
	}
}

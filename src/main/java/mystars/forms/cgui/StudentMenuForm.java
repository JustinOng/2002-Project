package mystars.forms.cgui;

import java.util.Arrays;
import java.util.List;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;

import mystars.entities.*;
import mystars.forms.*;

/**
 * This class manages the user interface for the student menu form.
 */
public class StudentMenuForm {
	/**
	 * Object used to contain response of this form. Class attribute to avoid error
	 * due to assignment from runnable
	 */
	private static StudentMenuResponse response;

	/**
	 * Displays list of options that a student can perform to manage courses. {@code
	 * regs} is also displayed on the form. If {@code regs} is empty (ie the student
	 * is not in any courses), the only option displayed is to register for a
	 * course.
	 * 
	 * @param gui  The graphical user interface object to display the UI on
	 * @param regs List of Registrations of the current user
	 * @return Selected option, or {@code null} if the form is closed without any
	 *         input
	 */
	public static StudentMenuResponse getResponse(MultiWindowTextGUI gui, List<Registration> regs) {
		final AbstractWindow window = new BasicWindow();

		response = null;

		// Create new panel object and set the layout as a grid.
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(4));

		if (regs != null && regs.size() > 0) {
			Table<String> table = new Table<String>("Course", "Title", "Index", "Status");
			for (Registration reg : regs) {
				Index index = reg.getIndex();
				Course course = index.getCourse();

				table.getTableModel().addRow(new String[] { course.getCourseCode(), course.getName(),
						Integer.toString(index.getIndexNo()), reg.getStatus().toString() });
			}
			panel.addComponent(table, GridLayout.createHorizontallyFilledLayoutData(4));
		} else {
			panel.addComponent(new Label("No currently registered courses"),
					GridLayout.createHorizontallyFilledLayoutData(4));
		}

		new Button("List Registered Courses", new Runnable() {
			public void run() {
				response = new StudentMenuResponse(StudentMenuResponse.Selected.ListRegistered);
				window.close();
			}
		}).addTo(panel);

		new Button("Check Vacancies", new Runnable() {
			public void run() {
				response = new StudentMenuResponse(StudentMenuResponse.Selected.ListVacancies);
				window.close();
			}
		}).addTo(panel);

		new Button("List Indexes", new Runnable() {
			public void run() {
				response = new StudentMenuResponse(StudentMenuResponse.Selected.ListIndexes);
				window.close();
			}
		}).addTo(panel);

		new Button("Register Course", new Runnable() {
			public void run() {
				response = new StudentMenuResponse(StudentMenuResponse.Selected.Register);
				window.close();
			}
		}).addTo(panel);

		if (regs != null && regs.size() > 0) {
			new Button("Drop Course", new Runnable() {
				public void run() {
					response = new StudentMenuResponse(StudentMenuResponse.Selected.Drop);
					window.close();
				}
			}).addTo(panel);

			new Button("Change Index", new Runnable() {
				public void run() {
					response = new StudentMenuResponse(StudentMenuResponse.Selected.Change);
					window.close();
				}
			}).addTo(panel);

			new Button("Swop Index", new Runnable() {
				public void run() {
					response = new StudentMenuResponse(StudentMenuResponse.Selected.Swop);
					window.close();
				}
			}).addTo(panel);
		}

		new Button("Logout", new Runnable() {
			public void run() {
				response = new StudentMenuResponse(StudentMenuResponse.Selected.Logout);
				window.close();
			}
		}).addTo(panel);

		window.setComponent(panel);

		window.setTitle("Student Menu");
		window.setHints(Arrays.asList(Window.Hint.CENTERED));

		gui.addWindowAndWait(window);

		return response;
	}
}

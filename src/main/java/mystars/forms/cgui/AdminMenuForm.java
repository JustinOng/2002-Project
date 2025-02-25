package mystars.forms.cgui;

import java.util.Arrays;

import com.googlecode.lanterna.gui2.*;

import mystars.forms.*;

/**
 * This class manages user interface for the administrator menu form.
 */
public class AdminMenuForm {
	/**
	 * Object used to contain response of this form. Class attribute to avoid error
	 * due to assignment from runnable
	 */
	private static AdminMenuResponse response;

	/**
	 * Displays admin menu with list of things the admin can do
	 * 
	 * @param gui The graphical user interface object to display the UI on
	 * @return Selected option, or {@code null} if the form is closed without any
	 *         input
	 */
	public static AdminMenuResponse getResponse(MultiWindowTextGUI gui) {
		final AbstractWindow window = new BasicWindow();

		response = null;

		// Create new panel object and set the layout as a grid.
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));

		// Insert textboxes, labels and buttons as required.
		new Button("Edit Student Access Period", new Runnable() {
			public void run() {
				response = new AdminMenuResponse(AdminMenuResponse.Selected.EditStudentAccessPeriod);
				window.close();
			}
		}).addTo(panel);

		new Button("Create Student", new Runnable() {
			public void run() {
				response = new AdminMenuResponse(AdminMenuResponse.Selected.CreateStudent);
				window.close();
			}
		}).addTo(panel);

		new Button("Create Course", new Runnable() {
			public void run() {
				response = new AdminMenuResponse(AdminMenuResponse.Selected.CreateCourse);
				window.close();
			}
		}).addTo(panel);

		new Button("Manage Courses", new Runnable() {
			public void run() {
				response = new AdminMenuResponse(AdminMenuResponse.Selected.ManageCourses);
				window.close();
			}
		}).addTo(panel);

		new Button("Check Vacancies", new Runnable() {
			public void run() {
				response = new AdminMenuResponse(AdminMenuResponse.Selected.ListVacancies);
				window.close();
			}
		}).addTo(panel);

		new Button("List Students", new Runnable() {
			public void run() {
				response = new AdminMenuResponse(AdminMenuResponse.Selected.ListStudents);
				window.close();
			}
		}).addTo(panel);

		new Button("Logout", new Runnable() {
			public void run() {
				response = new AdminMenuResponse(AdminMenuResponse.Selected.Logout);
				window.close();
			}
		}).addTo(panel);

		window.setComponent(panel);

		window.setTitle("Admin Menu");
		window.setHints(Arrays.asList(Window.Hint.CENTERED));

		gui.addWindowAndWait(window);

		return response;
	}
}

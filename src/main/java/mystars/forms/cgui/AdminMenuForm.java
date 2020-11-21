package mystars.forms.cgui;

import java.util.Arrays;

import com.googlecode.lanterna.gui2.*;

import mystars.forms.*;

/**
 * <h1>Class: AdminMenuForm</h1>
 * 
 * This class manages user interface for the administrator menu form.
 */
public class AdminMenuForm {
	/**
	 * The response object for the administrator menu form.
	 */
	private AdminMenuResponse response;

	/**
	 * Sets the parameters for the graphical user interface administrators will use
	 * when managing MySTARS.
	 * 
	 * @param gui The graphical user interface object.
	 * @return The administrator menu form response object.
	 */
	public AdminMenuResponse getResponse(MultiWindowTextGUI gui) {
		final AbstractWindow window = new BasicWindow();

		response = null;

		// Create new Jpanel object and set the layout as a grid.
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

		new Button("List Vacancies", new Runnable() {
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

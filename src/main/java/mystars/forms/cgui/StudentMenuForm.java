package mystars.forms.cgui;

import java.util.Arrays;
import java.util.List;

import com.googlecode.lanterna.gui2.*;

import mystars.forms.*;

/**
 * <h1>Class: StudentMenuForm</h1>
 * 
 * This class manages the user interface for the student menu form.
 */
public class StudentMenuForm {
	/**
	 * The response object for the student menu form.
	 */
	private StudentMenuResponse response;

	/**
	 * Sets the parameters for the graphical user interface students will use when
	 * using MySTARS.
	 * 
	 * @param gui     The graphical user interface object.
	 * @param courses The list of courses that students can register for.
	 * @return The student menu form response object.
	 */
	public StudentMenuResponse getResponse(MultiWindowTextGUI gui, List<String> courses) {
		final AbstractWindow window = new BasicWindow();

		response = null;

		// Create new Jpanel object and set the layout as a grid.
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(5));

		// Insert textboxes, labels and buttons as required.
		if (courses != null) {
			panel.addComponent(new Label("Registered Courses:\r\n" + String.join("\r\n", courses)),
					GridLayout.createHorizontallyFilledLayoutData(5));
		} else {
			panel.addComponent(new Label("No currently registered courses"),
					GridLayout.createHorizontallyFilledLayoutData(5));
		}

		new Button("Register Course", new Runnable() {
			public void run() {
				response = new StudentMenuResponse(StudentMenuResponse.Selected.Register);
				window.close();
			}
		}).addTo(panel);

		if (courses != null && courses.size() > 0) {
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

			new Button("Logout", new Runnable() {
				public void run() {
					response = new StudentMenuResponse(StudentMenuResponse.Selected.Logout);
					window.close();
				}
			}).addTo(panel);
		}

		window.setComponent(panel);

		window.setTitle("Student Menu");
		window.setHints(Arrays.asList(Window.Hint.CENTERED));

		gui.addWindowAndWait(window);

		return response;
	}
}

package mystars.forms.cgui;

import java.util.Arrays;
import java.util.List;

import com.googlecode.lanterna.gui2.*;

import mystars.forms.*;

/**
 * <h1>Class: CourseManagementForm</h1>
 * 
 * This class manages the user interface for the course management form.
 */
public class CourseManagementForm {
	/**
	 * The response object for the course management form.
	 */
	private CourseManagementResponse response;

	/**
	 * Sets the parameters for the graphical user interface administrators will use
	 * when managing courses.
	 * 
	 * @param gui     The graphical user interface object.
	 * @param courses The list of courses.
	 * @return The course management form response object.
	 */
	public CourseManagementResponse getResponse(MultiWindowTextGUI gui, List<String> courses) {
		final AbstractWindow window = new BasicWindow();

		response = null;

		// Create new Jpanel object and set the layout as a grid.
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(3));

		// Insert textboxes, labels and buttons as required.
		panel.addComponent(new Label("Course:"));
		final ComboBox<String> courseBox = new ComboBox<String>(courses);
		panel.addComponent(courseBox, GridLayout.createHorizontallyFilledLayoutData(2));

		new Button("Create Index", new Runnable() {
			public void run() {
				response = new CourseManagementResponse(courseBox.getSelectedItem(),
						CourseManagementResponse.Selected.CreateIndex);
				window.close();
			}
		}).addTo(panel);

		new Button("Manage Indexes", new Runnable() {
			public void run() {
				response = new CourseManagementResponse(courseBox.getSelectedItem(),
						CourseManagementResponse.Selected.ManageIndexes);
				window.close();
			}
		}).addTo(panel);

		new Button("List Students", new Runnable() {
			public void run() {
				response = new CourseManagementResponse(courseBox.getSelectedItem(),
						CourseManagementResponse.Selected.ListStudents);
				window.close();
			}
		}).addTo(panel);

		window.setComponent(panel);

		window.setTitle("Course Management");
		window.setHints(Arrays.asList(Window.Hint.CENTERED));
		KeyStrokeListener listener = new KeyStrokeListener();
		window.addWindowListener(listener);

		gui.addWindowAndWait(window);

		return response;
	}
}

package mystars.forms.cgui;

import java.util.Arrays;
import java.util.List;

import com.googlecode.lanterna.gui2.*;

import mystars.forms.*;

public class CourseManagementForm {
	private CourseManagementResponse response;

	public CourseManagementResponse getResponse(MultiWindowTextGUI gui, List<String> courses) {
		final AbstractWindow window = new BasicWindow();

		response = null;

		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(3));

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

		gui.addWindowAndWait(window);

		return response;
	}
}

package mystars.forms.cgui;

import java.util.Arrays;
import java.util.List;

import com.googlecode.lanterna.gui2.*;

import mystars.forms.*;

public class AdminMenuForm {
	private AdminMenuResponse response;

	public AdminMenuResponse getResponse(MultiWindowTextGUI gui) {
		final AbstractWindow window = new BasicWindow();
		
		response = null;

		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));
		
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

		window.setComponent(panel);

		window.setTitle("Admin Menu");
		window.setHints(Arrays.asList(Window.Hint.CENTERED));
		
		gui.addWindowAndWait(window);

		return response;
	}
}

package mystars.forms.cgui;

import java.util.Arrays;
import java.util.List;

import com.googlecode.lanterna.gui2.*;

import mystars.forms.*;

public class StudentMenuForm {
	private StudentMenuResponse response;

	public StudentMenuResponse getResponse(MultiWindowTextGUI gui, List<String> courses) {
		final AbstractWindow window = new BasicWindow();
		
		response = null;

		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(4));

		if (courses != null) {
			panel.addComponent(new Label("Registered Courses:\r\n" + String.join("\r\n", courses)),
					GridLayout.createHorizontallyFilledLayoutData(5));
		} else {
			panel.addComponent(new Label("No currently registered courses"),
					GridLayout.createHorizontallyFilledLayoutData(4));
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
		}

		window.setComponent(panel);

		window.setTitle("Student Menu");
		window.setHints(Arrays.asList(Window.Hint.CENTERED));
		
		gui.addWindowAndWait(window);

		return response;
	}
}

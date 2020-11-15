package mystars.forms.cgui;

import java.util.Arrays;
import java.util.List;

import com.googlecode.lanterna.gui2.*;

import mystars.forms.*;

public class IndexManagementForm {
	private IndexManagementResponse response;

	public IndexManagementResponse getResponse(MultiWindowTextGUI gui, String courseCode, List<String> indexes) {
		final AbstractWindow window = new BasicWindow();
		
		response = null;

		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(3));
		
		panel.addComponent(new Label("Index:"));
		final ComboBox<String> indexBox = new ComboBox<String>(indexes);
		panel.addComponent(indexBox, GridLayout.createHorizontallyFilledLayoutData(2));
		
		new Button("Create Lesson", new Runnable() {
			public void run() {
				response = new IndexManagementResponse(indexBox.getSelectedItem(), IndexManagementResponse.Selected.CreateLesson);
				window.close();
			}
		}).addTo(panel);
		
		new Button("List Students", new Runnable() {
			public void run() {
				response = new IndexManagementResponse(indexBox.getSelectedItem(), IndexManagementResponse.Selected.ListStudents);
				window.close();
			}
		}).addTo(panel);

		window.setComponent(panel);

		window.setTitle("Manage Course " + courseCode);
		window.setHints(Arrays.asList(Window.Hint.CENTERED));
		
		gui.addWindowAndWait(window);

		return response;
	}
}

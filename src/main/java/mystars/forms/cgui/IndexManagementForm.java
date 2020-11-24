package mystars.forms.cgui;

import java.util.Arrays;
import java.util.List;

import com.googlecode.lanterna.gui2.*;

import mystars.forms.*;

/**
 * <h1>Class: IndexManagementForm</h1>
 * 
 * This class manages the user interface for the course index management form.
 */
public class IndexManagementForm {
	/**
	 * Object used to contain response of this form. Class attribute to avoid error
	 * due to assignment from runnable
	 */
	private static IndexManagementResponse response;

	/**
	 * Sets the parameters for the graphical user interface administrators will use
	 * when managing course indexes.
	 * 
	 * @param gui        The graphical user interface object.
	 * @param courseCode Course code to display in the form title
	 * @param indexes    List of indexes that can be managed
	 * @return Selected option, or {@code null} if the form is cancelled/closed
	 *         without any input
	 */
	public static IndexManagementResponse getResponse(MultiWindowTextGUI gui, String courseCode, List<String> indexes) {
		final AbstractWindow window = new BasicWindow();

		response = null;

		// Create new panel object and set the layout as a grid.
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(3));

		// Insert textboxes, labels and buttons as required.
		panel.addComponent(new Label("Index:"));
		final ComboBox<String> indexBox = new ComboBox<String>(indexes);
		panel.addComponent(indexBox, GridLayout.createHorizontallyFilledLayoutData(2));

		new Button("Create Lesson", new Runnable() {
			public void run() {
				response = new IndexManagementResponse(indexBox.getSelectedItem(),
						IndexManagementResponse.Selected.CreateLesson);
				window.close();
			}
		}).addTo(panel);

		new Button("List Lessons", new Runnable() {
			public void run() {
				response = new IndexManagementResponse(indexBox.getSelectedItem(),
						IndexManagementResponse.Selected.ListLessons);
				window.close();
			}
		}).addTo(panel);

		new Button("List Students", new Runnable() {
			public void run() {
				response = new IndexManagementResponse(indexBox.getSelectedItem(),
						IndexManagementResponse.Selected.ListStudents);
				window.close();
			}
		}).addTo(panel);

		window.setComponent(panel);

		window.setTitle("Manage Course " + courseCode);
		window.setHints(Arrays.asList(Window.Hint.CENTERED));
		KeyStrokeListener listener = new KeyStrokeListener();
		window.addWindowListener(listener);

		gui.addWindowAndWait(window);

		return response;
	}
}

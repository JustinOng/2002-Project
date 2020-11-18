package mystars.forms.cgui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;

import mystars.forms.*;

/**
 * <h1>Class: CreateLessonForm</h1>
 * 
 * This class manages the user interface for the course index lesson creation form.
 */
public class CreateLessonForm {
	/**
	 * The response object for the course index lesson creation form.
	 */
	private CreateLessonResponse response;

	/**
	 * Sets the parameters for the graphical user interface administrators 
	 * will use when creating course index lessons.
	 * 
	 * @param gui			The graphical user interface object.
	 * @param index			The index of the course.
	 * @param lessonType	The lesson type of the index: Lecture, Tutorial or Lab.
	 * @param days			The days of the week which the lesson will be conducted.
	 * @return				The course index lesson creation form response object.
	 */
	public CreateLessonResponse getResponse(MultiWindowTextGUI gui, String index, List<String> lessonType,
			List<String> days) {
		final AbstractWindow window = new BasicWindow();

		response = null;

		// Create new Jpanel object and set the layout as a grid.
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(2));

		// Insert textboxes, labels and buttons as required.
		panel.addComponent(new Label("Lesson Type:"));
		final ComboBox<String> typeComboBox = new ComboBox<String>(lessonType).addTo(panel);

		panel.addComponent(new Label("Group Number:"));
		final TextBox groupInput = new TextBox().addTo(panel);

		panel.addComponent(new Label("Location:"));
		final TextBox locationInput = new TextBox().addTo(panel);

		panel.addComponent(new Label("Day:"));
		final ComboBox<String> dayComboBox = new ComboBox<String>(days).addTo(panel);

		panel.addComponent(new Label("Start Time:"));
		panel.addComponent(new Label("End Time:"));
		List<String> periods = new ArrayList<String>();
		for (int i = 0; i < 32; i++) {
			int hour = 8 + (i / 2);
			int minute = i % 2 == 1 ? 30 : 0;
			periods.add(String.format("%02d:%02d", hour, minute));
		}

		ComboBox<String> startComboBox = new ComboBox<String>(periods);
		panel.addComponent(startComboBox);

		ComboBox<String> endComboBox = new ComboBox<String>(periods);
		panel.addComponent(endComboBox);

		panel.addComponent(new Label("Weeks:"));
		CheckBoxList<String> weekCheckBoxes = new CheckBoxList<String>(new TerminalSize(13, 5));
		for (int i = 1; i <= 13; i++) {
			weekCheckBoxes.addItem(String.format("Week %d", i), true);
		}
		panel.addComponent(weekCheckBoxes);

		panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));

		new Button("Create", new Runnable() {
			public void run() {
				int start = startComboBox.getSelectedIndex();
				int end = endComboBox.getSelectedIndex();
				
				if (start >= end) {
					MessageDialog.showMessageDialog(gui, "Error", "Start time must be earlier than end time");
					return;
				}
				
				boolean[] weeks = new boolean[13];
				for (int i = 0; i < 13; i++) {
					weeks[i] = weekCheckBoxes.isChecked(i);
				}
				
				response = new CreateLessonResponse(typeComboBox.getSelectedItem(), dayComboBox.getSelectedItem(),
						locationInput.getText(), groupInput.getText(), weeks, startComboBox.getSelectedIndex(),
						endComboBox.getSelectedIndex());
				window.close();
			}
		}).addTo(panel);

		window.setComponent(panel);

		KeyStrokeListener listener = new KeyStrokeListener();
		window.setTitle("Create Lesson on " + index);
		window.addWindowListener(listener);
		window.setHints(Arrays.asList(Window.Hint.CENTERED));

		gui.addWindowAndWait(window);

		return response;
	}
}

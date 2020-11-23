package mystars.forms.cgui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;

import mystars.forms.*;

/**
 * <h1>Class: CreateLessonForm</h1>
 * 
 * This class manages the user interface for the course index lesson creation
 * form.
 */
public class CreateLessonForm {
	/**
	 * Object used to contain response of this form. Class attribute to avoid error
	 * due to assignment from runnable
	 */
	private CreateLessonResponse response;

	/**
	 * Displays form requesting user input for creating a lesson
	 * 
	 * @param gui        The graphical user interface object.
	 * @param index      Parent index number to be displayed on the form
	 * @param lessonType The list of lesson types that the lesson can take.
	 * @param days       The list of days that the lesson can be conducted on.
	 * @return User input to be used to create a new Lesson, or {@code null} if the
	 *         form is cancelled/closed without any input
	 */
	public CreateLessonResponse getResponse(MultiWindowTextGUI gui, String index, List<String> lessonType,
			List<String> days) {
		final AbstractWindow window = new BasicWindow();

		response = null;

		// Create new panel object and set the layout as a grid.
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(2));

		// Insert textboxes, labels and buttons as required.
		panel.addComponent(new Label("Lesson Type:"));
		final ComboBox<String> typeComboBox = new ComboBox<String>(lessonType).addTo(panel);

		panel.addComponent(new Label("Group Number:"));
		final TextBox groupInput = new TextBox().addTo(panel);
		groupInput.setValidationPattern(Pattern.compile("^[A-Z0-9]+$"));

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
				String location = locationInput.getText();
				String group = groupInput.getText();

				if (location.isBlank()) {
					MessageDialog.showMessageDialog(gui, "Error", "Location cannot be blank");
					return;
				}

				if (group.isBlank()) {
					MessageDialog.showMessageDialog(gui, "Error", "Group cannot be blank");
					return;
				}

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
						location, group, weeks, startComboBox.getSelectedIndex(), endComboBox.getSelectedIndex());
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

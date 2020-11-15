package mystars.forms.cgui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;

import java.util.Arrays;
import java.util.List;

public class DisplayStudentList {
	public static void show(MultiWindowTextGUI gui, String title, List<String[]> students) {
		final AbstractWindow window = new BasicWindow();

		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));

		if (students.size() > 0) {
			Table<String> table = new Table<String>("Name", "Gender", "Nationality");
			for (String[] data : students) {
				table.getTableModel().addRow(data);
			}
			panel.addComponent(table);
		} else {
			panel.addComponent(new Label("No students registered"));
		}

		panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));

		new Button("Back", new Runnable() {
			public void run() {
				window.close();
			}
		}).addTo(panel);

		window.setComponent(panel);

		KeyStrokeListener listener = new KeyStrokeListener();
		window.setTitle(title);
		window.addWindowListener(listener);
		window.setHints(Arrays.asList(Window.Hint.CENTERED));

		gui.addWindowAndWait(window);

		return;
	}
}

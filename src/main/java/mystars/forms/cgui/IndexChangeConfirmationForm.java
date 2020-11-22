package mystars.forms.cgui;

import java.util.Arrays;
import java.util.List;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;

/**
 * <h1>Class: IndexChangeConfirmationForm</h1>
 * 
 * This class manages the user interface for confirming an index change.
 */
public class IndexChangeConfirmationForm {
	private boolean response;
	
	public boolean confirm(MultiWindowTextGUI gui, String title, String description, String labelA,
			List<String[]> lessonsA, String labelB, List<String[]> lessonsB) {
		final AbstractWindow window = new BasicWindow();
		
		response = false;

		// Create new panel object and set the layout as a grid.
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(2));
		
		panel.addComponent(new Label(description), GridLayout.createHorizontallyFilledLayoutData(2));
		
		panel.addComponent(new Label(labelA));
		panel.addComponent(new Label(labelB));
		
		Table<String> tableA = new Table<String>("Class Type", "Group", "Day", "Time", "Venue");
		for (String[] data : lessonsA) {
			tableA.getTableModel().addRow(data);
		}
		panel.addComponent(tableA);
		
		Table<String> tableB = new Table<String>("Class Type", "Group", "Day", "Time", "Venue");
		for (String[] data : lessonsB) {
			tableB.getTableModel().addRow(data);
		}
		panel.addComponent(tableB);

		new Button("Cancel", new Runnable() {
			public void run() {
				response = false;
				window.close();
			}
		}).addTo(panel);

		new Button("Confirm", new Runnable() {
			public void run() {
				response = true;
				window.close();
			}
		}).addTo(panel);

		window.setComponent(panel);

		window.setTitle(title);
		KeyStrokeListener listener = new KeyStrokeListener();
		window.addWindowListener(listener);
		window.setHints(Arrays.asList(Window.Hint.CENTERED));

		gui.addWindowAndWait(window);

		return response;
	}
}

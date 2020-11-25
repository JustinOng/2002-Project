package mystars.forms.cgui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;

import java.util.Arrays;
import java.util.List;

/**
 * This class displays a list of items
 */
public class DisplayList<T> {
	/**
	 * Interface defining callback function
	 * 
	 * @param <T> Type of item to retrieve properties for
	 */
	public interface GetProperties<T> {
		/**
		 * Called on every item passed to {@code show(...)} to retrieve properties for
		 * display
		 * 
		 * @param item item to retrieve properties from
		 * @return String[] containing list of properties to display
		 */
		String[] run(T item);
	}

	/**
	 * Displays a list of items with the given title in a table
	 * 
	 * @param gui          The graphical user interface object to display the UI on
	 * @param title        The title of the form.
	 * @param emptyMessage Message to display if {@code items} is empty
	 * @param headers      List of table headers
	 * @param items        List of items to display. {@code handler.getProperties}
	 *                     is called on every item to get the properties to display.
	 * @param handler      Function to be called to expand item into its properties
	 */
	public void show(MultiWindowTextGUI gui, String title, String emptyMessage, String[] headers, List<T> items,
			GetProperties<T> handler) {
		final AbstractWindow window = new BasicWindow();

		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(2));

		if (items.size() > 0) {
			Table<String> table = new Table<String>(headers);
			table.setVisibleRows(10);

			for (T item : items) {
				table.getTableModel().addRow(handler.run(item));
			}
			panel.addComponent(table);
		} else {
			panel.addComponent(new Label(emptyMessage), GridLayout.createHorizontallyFilledLayoutData(2));
		}

		panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));

		new Button("Back", new Runnable() {
			public void run() {
				window.close();
			}
		}).addTo(panel);

		window.setComponent(panel);

		window.setTitle(title);
		KeyStrokeListener listener = new KeyStrokeListener();
		window.addWindowListener(listener);
		window.setHints(Arrays.asList(Window.Hint.CENTERED));

		gui.addWindowAndWait(window);
	}
}

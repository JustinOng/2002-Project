package mystars.forms.cgui;

import java.util.List;
import java.util.Arrays;

import com.googlecode.lanterna.gui2.*;

import mystars.forms.*;

/**
 * <h1>Class: ItemSelectorForm</h1>
 * 
 * This class manages the user interface for selection of options on a form.
 */
public class ItemSelectorForm implements RadioBoxList.Listener {
	/**
	 * The new window to be generated as a prompt.
	 */
	private AbstractWindow window = new BasicWindow();

	/**
	 * Object used to contain response of this form. Class attribute to avoid error
	 * due to assignment from runnable
	 */
	private TextResponse response;

	/**
	 * The list of options to be displayed on the form.
	 */
	private List<String> items;

	/**
	 * Displays a generic form requesting that the user select one of {@code items}
	 * 
	 * @param gui   The graphical user interface object.
	 * @param title Title to label form with
	 * @param items List of items that can be selected
	 * @return Selected item, or {@code null} if the form is cancelled/closed
	 *         without any input
	 */
	public TextResponse getResponse(MultiWindowTextGUI gui, String title, List<String> items) {
		response = null;

		this.items = items;

		// Create a radio button list and their listeners.
		RadioBoxList<String> radioBoxList = new RadioBoxList<String>();
		radioBoxList.addListener(this);
		for (String s : items) {
			radioBoxList.addItem(s);
		}
		window.setComponent(radioBoxList);

		window.setTitle(title);
		window.setHints(Arrays.asList(Window.Hint.CENTERED));

		KeyStrokeListener listener = new KeyStrokeListener();
		window.addWindowListener(listener);

		gui.addWindowAndWait(window);

		return response;
	}

	/**
	 * Change the corresponding selection when the user chooses a different option.
	 * 
	 * @param selectedIndex     The currently selected option index.
	 * @param previousSelection The previously selected option index.
	 */
	public void onSelectionChanged(int selectedIndex, int previousSelection) {
		response = new TextResponse(items.get(selectedIndex));
		window.close();
	}
}

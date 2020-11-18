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
	 * The text response object.
	 */
	private TextResponse response;
	
	/**
	 * The list of options to be displayed on the form.
	 */
	private List<String> items;
	
	/**
	 * Sets the parameters for the graphical user interface users will use
	 * when making selections for options listed on the form.
	 * 
	 * @param gui	The graphical user interface object.
	 * @param title The title of the form.
	 * @param items	The list of options to be displayed on the form.
	 * @return		The text response object.
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
	 * @param selectedIndex 	The currently selected option index.
	 * @param previousSelection The previously selected option index.
	 */
	public void onSelectionChanged(int selectedIndex, int previousSelection) {
		response = new TextResponse(items.get(selectedIndex));
		window.close();
	}
}

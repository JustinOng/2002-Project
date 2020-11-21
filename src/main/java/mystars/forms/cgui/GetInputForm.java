package mystars.forms.cgui;

import java.util.regex.Pattern;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialog;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;

/**
 * <h1>Class: GetInputForm</h1>
 * 
 * This manages the user interface when getting inputs from the user as a string
 * or numerical value.
 */
public class GetInputForm {
	/**
	 * Shows a dialog prompt for the user's input, and returns a string input
	 * obtained from the user.
	 * 
	 * @param gui         The graphical user interface object.
	 * @param title       The title of the prompt window.
	 * @param description The description for the input in the prompt window.
	 * @return The string containing the user's input.
	 */
	public static String getText(MultiWindowTextGUI gui, String title, String description) {
		return TextInputDialog.showDialog(gui, title, description, "");
	}

	/**
	 * Shows a dialog prompt for the user's input, and returns a numerical input
	 * entered by the user.
	 * 
	 * @param gui         The graphical user interface object.
	 * @param title       The title of the prompt window.
	 * @param description The description for the input in the prompt window.
	 * @return The numerical value of the user's input.
	 */
	public static Integer getInt(MultiWindowTextGUI gui, String title, String description) {
		String input = new TextInputDialogBuilder().setTitle(title).setDescription(description)
				.setValidationPattern(Pattern.compile("^[0-9]+$"), "Inputs must be numerical!").build().showDialog(gui);

		if (input == null)
			return null;

		return Integer.parseInt(input);
	}
}

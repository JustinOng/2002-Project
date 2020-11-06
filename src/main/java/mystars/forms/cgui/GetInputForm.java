package mystars.forms.cgui;

import java.util.regex.Pattern;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialog;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;

import mystars.forms.*;

public class GetInputForm {
	public static String getText(MultiWindowTextGUI gui, String title, String description) {
		return TextInputDialog.showDialog(gui, title, description, "");
	}

	public static Integer getInt(MultiWindowTextGUI gui, String title, String description) {
		String input = new TextInputDialogBuilder().setTitle(title).setDescription(description)
				.setValidationPattern(Pattern.compile("^[0-9]+$"), "Inputs must be numerical!").build()
				.showDialog(gui);
		
		if (input == null) return null;
		
		return Integer.parseInt(input);
	}
}

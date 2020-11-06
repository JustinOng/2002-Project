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

	public static int getInt(MultiWindowTextGUI gui, String title, String description) {
		int val = Integer.parseInt(new TextInputDialogBuilder().setTitle(title).setDescription(description)
				.setValidationPattern(Pattern.compile("^[0-9]+$"), "Inputs must be numerical!").build()
				.showDialog(gui));
		return val;
	}
}

package mystars.forms.cgui;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialog;

import mystars.forms.*;

public class TextInput {
	public static TextResponse getResponse(MultiWindowTextGUI gui, String title, String description) {
		return new TextResponse(TextInputDialog.showDialog(gui, title, description, ""));
	}
}

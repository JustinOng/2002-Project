package mystars;

import java.io.IOException;
import java.util.Arrays;

import mystars.forms.*;
import mystars.forms.cgui.ConsoleGraphicUserInterface;

public class MySTARS {
	private IUserInterface ui = new ConsoleGraphicUserInterface();

	public MySTARS() throws IOException {
//		LoginResponse response = ui.renderLoginForm();
//		System.out.println(response.getUsername());
//		SelectorResponse response = ui.renderStudentMenuForm(Arrays.asList("Course A", "Course B"));
//		System.out.println(response.getText());
//		TextResponse response = ui.renderItemSelectorForm("Courses", Arrays.asList("Course A", "Course B"));
//		System.out.println(response.getText());
//		TextResponse response = ui.renderTextInput("Title!", "Description?");
//		System.out.println(response.getText());
		IndexSwopResponse response = ui.renderIndexSwopForm();
//		System.out.println(response.getText());
	}
}

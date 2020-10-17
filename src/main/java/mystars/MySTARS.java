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
//		System.out.println(response.getSelected());
		SelectorResponse response = ui.renderItemSelectorForm("Courses", Arrays.asList("Course A", "Course B"));
		System.out.println(response.getSelected());
	}
}

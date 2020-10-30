package mystars.forms.cgui;

import java.util.Arrays;
import java.util.regex.Pattern;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import mystars.enums.School;
import mystars.forms.*;

public class CreateCourseForm {
	private CreateCourseResponse response;

	public CreateCourseResponse getResponse(MultiWindowTextGUI gui) {
		final AbstractWindow window = new BasicWindow();

		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(2));

		final Label status = new Label("");
		panel.addComponent(status, GridLayout.createHorizontallyFilledLayoutData(2));

		panel.addComponent(new Label("Code:"));
		final TextBox codeInput = new TextBox().addTo(panel);

		panel.addComponent(new Label("Name:"));
		final TextBox nameInput = new TextBox().addTo(panel);

		panel.addComponent(new Label("School:"));
		final ComboBox<String> schoolComboBox = new ComboBox<String>(
				Arrays.toString(School.values()).replaceAll("^.|.$", "").split(", ")).addTo(panel);

		panel.addComponent(new Label("AUs:"));
		final TextBox auInput = new TextBox().addTo(panel);
		auInput.setValidationPattern(Pattern.compile("^[0-9]+$"));

		panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));

		new Button("Create", new Runnable() {
			public void run() {
				response = new CreateCourseResponse(codeInput.getText(), nameInput.getText(),
						School.valueOf(schoolComboBox.getText()), Integer.parseInt(auInput.getText()));
				window.close();
			}
		}).addTo(panel);

		window.setComponent(panel);

		KeyStrokeListener listener = new KeyStrokeListener();
		window.setTitle("Login");
		window.addWindowListener(listener);
		window.setHints(Arrays.asList(Window.Hint.CENTERED));

		gui.addWindowAndWait(window);

		return response;
	}
}

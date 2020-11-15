package mystars.forms.cgui;

import java.util.Arrays;
import java.util.regex.Pattern;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import mystars.forms.*;

public class CreateIndexForm {
	private CreateIndexResponse response;
	
	public CreateIndexResponse getResponse(MultiWindowTextGUI gui, String course) {
		final AbstractWindow window = new BasicWindow();
		
		response = null;

		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(2));

		panel.addComponent(new Label("Number:"));
		final TextBox numberInput = new TextBox().addTo(panel);
		numberInput.setValidationPattern(Pattern.compile("^[0-9]+$"));

		panel.addComponent(new Label("Maximum Enrolled:"));
		final TextBox maxEnrolledInput = new TextBox().addTo(panel);
		maxEnrolledInput.setValidationPattern(Pattern.compile("^[0-9]+$"));

		panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));

		new Button("Create", new Runnable() {
			public void run() {
				response = new CreateIndexResponse(Integer.parseInt(numberInput.getText()), Integer.parseInt(maxEnrolledInput.getText()));
				window.close();
			}
		}).addTo(panel);

		window.setComponent(panel);

		KeyStrokeListener listener = new KeyStrokeListener();
		window.setTitle("Create Index on " + course);
		window.addWindowListener(listener);
		window.setHints(Arrays.asList(Window.Hint.CENTERED));

		gui.addWindowAndWait(window);

		return response;
	}
}

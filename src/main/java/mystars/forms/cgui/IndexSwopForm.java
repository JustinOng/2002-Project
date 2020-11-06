package mystars.forms.cgui;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import mystars.forms.*;

public class IndexSwopForm {
	private IndexSwopResponse response;

	public IndexSwopResponse getResponse(MultiWindowTextGUI gui) {
		final AbstractWindow window = new BasicWindow();
		
		response = null;

		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(2));

		final Label status = new Label("");
		panel.addComponent(status, GridLayout.createHorizontallyFilledLayoutData(2));

		panel.addComponent(new Label("Your Index Number:"));
		final TextBox indexAInput = new TextBox().addTo(panel);
		indexAInput.setValidationPattern(Pattern.compile("^[0-9]+$"));

		panel.addComponent(new Label("Peer's Index Number:"));
		final TextBox indexBInput = new TextBox().addTo(panel);
		indexBInput.setValidationPattern(Pattern.compile("^[0-9]+$"));

		panel.addComponent(new Label("Peer's Username:"));
		final TextBox usernameInput = new TextBox().addTo(panel);

		panel.addComponent(new Label("Peer's Password:"));
		final TextBox passwordInput = new TextBox().addTo(panel);
		passwordInput.setMask('*');

		panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));

		new Button("Swop", new Runnable() {
			public void run() {
				response = new IndexSwopResponse(Integer.parseInt(indexAInput.getText()),
						Integer.parseInt(indexBInput.getText()), usernameInput.getText(), passwordInput.getText());
				window.close();
			}
		}).addTo(panel);

		window.setComponent(panel);

		KeyStrokeListener listener = new KeyStrokeListener();
		window.setTitle("Swop Index");
		window.addWindowListener(listener);
		window.setHints(Arrays.asList(Window.Hint.CENTERED));

		gui.addWindowAndWait(window);

		return response;
	}
}

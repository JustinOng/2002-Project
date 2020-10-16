package mystars.forms.cgui;

import java.io.IOException;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import mystars.forms.IUserInterface;
import mystars.forms.IUserInterfaceObserver;

public class ConsoleGraphicUserInterface implements IUserInterface {
	private MultiWindowTextGUI gui;

	private LoginForm loginForm = new LoginForm();

	public ConsoleGraphicUserInterface(IUserInterfaceObserver observer) throws IOException {
		Terminal terminal = new DefaultTerminalFactory().createTerminal();
		Screen screen = new TerminalScreen(terminal);
		screen.startScreen();

		gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));

		loginForm.addObserver(observer);
	}

	public void renderLoginForm() {
		gui.addWindowAndWait(loginForm.getWindow());
	}
}

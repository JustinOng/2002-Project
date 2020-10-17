package mystars.forms.cgui;

import java.io.IOException;
import java.util.List;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import mystars.forms.*;

public class ConsoleGraphicUserInterface implements IUserInterface {
	private MultiWindowTextGUI gui;

	private LoginForm loginForm = new LoginForm();
	private StudentMenuForm studentMenuForm = new StudentMenuForm();
	private ItemSelectorForm itemSelectorForm = new ItemSelectorForm();

	public ConsoleGraphicUserInterface() throws IOException {
		Terminal terminal = new DefaultTerminalFactory().createTerminal();
		Screen screen = new TerminalScreen(terminal);
		screen.startScreen();

		gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
	}

	public LoginResponse renderLoginForm() {
		return loginForm.getResponse(gui);
	}
	
	public SelectorResponse renderStudentMenuForm(List<String> courses) {
		return studentMenuForm.getResponse(gui, courses);
	}

	public SelectorResponse renderItemSelectorForm(String itemType, List<String> items) {
		return itemSelectorForm.getResponse(gui, itemType, items);
	}

	public void renderCourseInputForm() {
		// TODO Auto-generated method stub
		
	}
}

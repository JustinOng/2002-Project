package mystars.forms.cgui;

import java.io.IOException;
import java.util.List;

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
	private ItemSelectorForm itemSelectorForm = new ItemSelectorForm();

	public ConsoleGraphicUserInterface(IUserInterfaceObserver observer) throws IOException {
		Terminal terminal = new DefaultTerminalFactory().createTerminal();
		Screen screen = new TerminalScreen(terminal);
		screen.startScreen();

		gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));

		loginForm.addObserver(observer);
		itemSelectorForm.addObserver(observer);
	}

	public void renderLoginForm() {
		gui.addWindowAndWait(loginForm.getWindow());
	}

	public void renderItemSelectorForm(String itemType, List<String> items) {
		itemSelectorForm.setItemType(itemType);
		itemSelectorForm.setItemList(items);
		
		gui.addWindow(itemSelectorForm.getWindow());
	}
}

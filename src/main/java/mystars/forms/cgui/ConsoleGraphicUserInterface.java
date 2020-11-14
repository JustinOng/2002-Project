package mystars.forms.cgui;

import java.awt.Font;
import java.io.IOException;
import java.util.List;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;

import mystars.forms.*;

public class ConsoleGraphicUserInterface implements IUserInterface {
	private MultiWindowTextGUI gui;

	private LoginForm loginForm = new LoginForm();
	private StudentMenuForm studentMenuForm = new StudentMenuForm();
	private AdminMenuForm adminMenuForm = new AdminMenuForm();
	private ItemSelectorForm itemSelectorForm = new ItemSelectorForm();
	private IndexSwopForm indexSwopForm = new IndexSwopForm();
	private CreateStudentForm createStudentForm = new CreateStudentForm();
	private CreateCourseForm createCourseForm = new CreateCourseForm();
	private CreateIndexForm createIndexForm = new CreateIndexForm();

	public ConsoleGraphicUserInterface() throws IOException {
		DefaultTerminalFactory factory = new DefaultTerminalFactory();
	    SwingTerminalFontConfiguration config = new SwingTerminalFontConfiguration(true, AWTTerminalFontConfiguration.BoldMode.NOTHING, new Font("Consolas", Font.PLAIN, 20));
	    
	    factory.setTerminalEmulatorFontConfiguration(config);
	    
	    Terminal terminal = factory.createTerminal();
		Screen screen = new TerminalScreen(terminal);
		screen.startScreen();

		gui = new AppUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
	}

	public LoginResponse renderLoginForm() {
		return loginForm.getResponse(gui);
	}

	public StudentMenuResponse renderStudentMenuForm(List<String> courses) {
		return studentMenuForm.getResponse(gui, courses);
	}

	public TextResponse renderItemSelectorForm(String title, List<String> items) {
		return itemSelectorForm.getResponse(gui, title, items);
	}

	public IndexSwopResponse renderIndexSwopForm() {
		return indexSwopForm.getResponse(gui);
	}

	public CreateStudentResponse renderCreateStudentForm() {
		return createStudentForm.getResponse(gui);
	}

	public CreateCourseResponse renderCreateCourseForm() {
		return createCourseForm.getResponse(gui);
	}

	public CreateIndexResponse renderCreateIndexForm() {
		return createIndexForm.getResponse(gui);
	}

	public String getText(String title, String description) {
		return GetInputForm.getText(gui, title, description);
	}

	public Integer getInt(String title, String description) {
		return GetInputForm.getInt(gui, title, description);
	}

	public void renderDialog(String title, String msg) {
		MessageDialog.showMessageDialog(gui, title, msg);
	}

	public AdminMenuResponse renderAdminMenuForm() {
		return adminMenuForm.getResponse(gui);
	}
}

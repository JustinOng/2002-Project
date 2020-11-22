package mystars.forms.cgui;

import java.awt.Font;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;

import mystars.forms.*;

/**
 * <h1>Class: ConsoleGraphicUserInterface</h1>
 * 
 * This class creates the respective form objects and manages the graphical user
 * interfaces for the console that users will use.
 */
public class ConsoleGraphicUserInterface implements IUserInterface {
	/**
	 * Lanterna GUI to display windows on
	 */
	private MultiWindowTextGUI gui;

	/**
	 * The login form.
	 */
	private LoginForm loginForm = new LoginForm();

	/**
	 * The student menu form.
	 */
	private StudentMenuForm studentMenuForm = new StudentMenuForm();

	/**
	 * The administrators menu form.
	 */
	private AdminMenuForm adminMenuForm = new AdminMenuForm();

	/**
	 * The item selector form.
	 */
	private ItemSelectorForm itemSelectorForm = new ItemSelectorForm();

	/**
	 * The swop index form.
	 */
	private IndexSwopForm indexSwopForm = new IndexSwopForm();

	/**
	 * The create new student form.
	 */
	private CreateStudentForm createStudentForm = new CreateStudentForm();

	/**
	 * The create new course form.
	 */
	private CreateCourseForm createCourseForm = new CreateCourseForm();

	/**
	 * The create new index form.
	 */
	private CreateIndexForm createIndexForm = new CreateIndexForm();

	/**
	 * The create new lesson form.
	 */
	private CreateLessonForm createLessonForm = new CreateLessonForm();

	/**
	 * The access period form.
	 */
	private AccessPeriodForm accessPeriodForm = new AccessPeriodForm();

	/**
	 * The course management form.
	 */
	private CourseManagementForm courseManagementForm = new CourseManagementForm();

	/**
	 * The index management form.
	 */
	private IndexManagementForm indexManagementForm = new IndexManagementForm();
	
	/**
	 * Generic form to confirm changing between two indexes
	 */
	private IndexChangeConfirmationForm indexChangeConfirmationForm = new IndexChangeConfirmationForm();

	/**
	 * Creates a new instance of this class and initialises Lanterna gui. Attempt to
	 * turn off anti-aliasing on the font used because it has resulted in blurry
	 * fonts on certain machine configurations.
	 * 
	 * @throws IOException if an UI error occured
	 */
	public ConsoleGraphicUserInterface() throws IOException {
		DefaultTerminalFactory factory = new DefaultTerminalFactory();
		SwingTerminalFontConfiguration config = new SwingTerminalFontConfiguration(true,
				AWTTerminalFontConfiguration.BoldMode.NOTHING, new Font("Consolas", Font.PLAIN, 20));

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

	public CreateStudentResponse renderCreateStudentForm(List<String> genders, List<String> nationalities) {
		return createStudentForm.getResponse(gui, genders, nationalities);
	}

	public CreateCourseResponse renderCreateCourseForm(List<String> schools) {
		return createCourseForm.getResponse(gui, schools);
	}

	public CreateIndexResponse renderCreateIndexForm(String course) {
		return createIndexForm.getResponse(gui, course);
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

	public AccessPeriodResponse renderAccessPeriodForm(String curAccessPeriod) {
		return accessPeriodForm.getResponse(gui, curAccessPeriod);
	}

	public CourseManagementResponse renderCourseManagementForm(List<String> courses) {
		return courseManagementForm.getResponse(gui, courses);
	}

	public IndexManagementResponse renderIndexManagementForm(String courseCode, List<String> indexes) {
		return indexManagementForm.getResponse(gui, courseCode, indexes);
	}

	public CreateLessonResponse renderCreateLessonForm(String index, List<String> lessonType, List<String> days) {
		return createLessonForm.getResponse(gui, index, lessonType, days);
	}

	public void renderStudentList(String title, List<String[]> students) {
		DisplayStudentList.show(gui, title, students);
	}

	public boolean renderConfirmation(String title, String msg) {
		return new MessageDialogBuilder().setTitle(title).setText(msg).addButton(MessageDialogButton.Cancel)
				.addButton(MessageDialogButton.OK).build().showDialog(gui) == MessageDialogButton.OK;
	}

	public void renderIndexInfo(String title, Map<String, List<String[]>> indexes) {
		DisplayIndexList.show(gui, title, indexes);
	}

	public boolean renderIndexChangeConfirmation(String title, String description, String labelA,
			List<String[]> lessonsA, String labelB, List<String[]> lessonsB) {
		return indexChangeConfirmationForm.confirm(gui, title, description, labelA, lessonsA, labelB, lessonsB);
	}
}

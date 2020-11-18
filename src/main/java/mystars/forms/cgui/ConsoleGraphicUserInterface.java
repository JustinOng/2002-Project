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

/**
 * <h1>Class: ConsoleGraphicUserInterface</h1>
 * 
 * This class creates the respective form objects and manages the 
 * graphical user interfaces for the console that users will use.
 */
public class ConsoleGraphicUserInterface implements IUserInterface {
	/**
	 * The mutli window text graphical user interface.
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
	 * 
	 * @throws IOException
	 */
	public ConsoleGraphicUserInterface() throws IOException {
		DefaultTerminalFactory factory = new DefaultTerminalFactory();
	    SwingTerminalFontConfiguration config = new SwingTerminalFontConfiguration(true, AWTTerminalFontConfiguration.BoldMode.NOTHING, new Font("Consolas", Font.PLAIN, 20));
	    
	    factory.setTerminalEmulatorFontConfiguration(config);
	    
	    Terminal terminal = factory.createTerminal();
		Screen screen = new TerminalScreen(terminal);
		screen.startScreen();

		gui = new AppUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
	}

	/**
	 * Return the response from the user login form.
	 */
	public LoginResponse renderLoginForm() {
		return loginForm.getResponse(gui);
	}

	/**
	 * Return the response from the student menu form.
	 */
	public StudentMenuResponse renderStudentMenuForm(List<String> courses) {
		return studentMenuForm.getResponse(gui, courses);
	}

	/**
	 * Return the response from the item selector form.
	 */
	public TextResponse renderItemSelectorForm(String title, List<String> items) {
		return itemSelectorForm.getResponse(gui, title, items);
	}

	/**
	 * Return the response from the index swop form.
	 */
	public IndexSwopResponse renderIndexSwopForm() {
		return indexSwopForm.getResponse(gui);
	}

	/**
	 * Return the response from the create new student form.
	 */
	public CreateStudentResponse renderCreateStudentForm(List<String> genders, List<String> nationalities) {
		return createStudentForm.getResponse(gui, genders, nationalities);
	}

	/**
	 * Return the response from the create new course form.
	 */
	public CreateCourseResponse renderCreateCourseForm(List<String> schools) {
		return createCourseForm.getResponse(gui, schools);
	}

	/**
	 * Return the response from the create index form.
	 */
	public CreateIndexResponse renderCreateIndexForm(String course) {
		return createIndexForm.getResponse(gui, course);
	}

	/**
	 * Return the string response from the input form.
	 */
	public String getText(String title, String description) {
		return GetInputForm.getText(gui, title, description);
	}

	/**
	 * Return the numerical response from the get input form.
	 */
	public Integer getInt(String title, String description) {
		return GetInputForm.getInt(gui, title, description);
	}

	/**
	 * Show the dialog prompt with the passed in title and message.
	 */
	public void renderDialog(String title, String msg) {
		MessageDialog.showMessageDialog(gui, title, msg);
	}

	/**
	 * Return the response from the admin menu form.
	 */
	public AdminMenuResponse renderAdminMenuForm() {
		return adminMenuForm.getResponse(gui);
	}

	/**
	 * Return the response from the access period form.
	 */
	public AccessPeriodResponse renderAccessPeriodForm(String curAccessPeriod) {
		return accessPeriodForm.getResponse(gui, curAccessPeriod);
	}

	/**
	 * Return the response from the course management form.
	 */
	public CourseManagementResponse renderCourseManagementForm(List<String> courses) {
		return courseManagementForm.getResponse(gui, courses);
	}

	/**
	 * Return the response from the index management form.
	 */
	public IndexManagementResponse renderIndexManagementForm(String courseCode, List<String> indexes) {
		return indexManagementForm.getResponse(gui, courseCode, indexes);
	}

	/**
	 * Return the response from the create new lesson form.
	 */
	public CreateLessonResponse renderCreateLessonForm(String index, List<String> lessonType, List<String> days) {
		return createLessonForm.getResponse(gui, index, lessonType, days);
	}

	/**
	 * Display the list of students.
	 */
	public void renderStudentList(String title, List<String[]> students) {
		DisplayStudentList.show(gui, title, students);
	}
}

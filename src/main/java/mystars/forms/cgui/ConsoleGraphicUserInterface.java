package mystars.forms.cgui;

import java.awt.Font;
import java.io.IOException;
import java.util.List;

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

import mystars.entities.*;
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

		try {
			SwingTerminalFontConfiguration config = new SwingTerminalFontConfiguration(true,
					AWTTerminalFontConfiguration.BoldMode.NOTHING, new Font("Consolas", Font.PLAIN, 20));

			factory.setTerminalEmulatorFontConfiguration(config);
		} catch (IllegalArgumentException e) {
			// this can fail if Consolas isn't monospaced
			// observed to fail only in terminal environments so we're just going to swallow
			// the exception silently since the default font in a terminal environment isn't
			// going to be blurry
		}

		Terminal terminal = factory.createTerminal();
		Screen screen = new TerminalScreen(terminal);
		screen.startScreen();

		gui = new AppUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
	}

	// The following functions will inherit the javadocs from IUserInterface

	@Override
	/**
	 * Render and get a response from the form for the user's login. 
	 */
	public LoginResponse renderLoginForm() {
		return loginForm.getResponse(gui);
	}

	@Override
	/**
	 * Render and get a response from the form for the student menu.
	 */
	public StudentMenuResponse renderStudentMenuForm(List<Registration> regs) {
		return studentMenuForm.getResponse(gui, regs);
	}

	@Override
	/**
	 * Render and get a response from the form for the item selector.
	 */
	public TextResponse renderItemSelectorForm(String title, List<String> items) {
		return itemSelectorForm.getResponse(gui, title, items);
	}

	@Override
	/**
	 * Render and get a response from the form for swopping index with peer.
	 */
	public IndexSwopResponse renderIndexSwopForm() {
		return indexSwopForm.getResponse(gui);
	}

	@Override
	/**
	 * Render and get a response from the form for the creation of students.
	 */
	public CreateStudentResponse renderCreateStudentForm(List<String> genders, List<String> nationalities) {
		return createStudentForm.getResponse(gui, genders, nationalities);
	}

	@Override
	/**
	 * Render and get a response from the form for the creation of courses.
	 */
	public CreateCourseResponse renderCreateCourseForm(List<String> schools) {
		return createCourseForm.getResponse(gui, schools);
	}

	@Override
	/**
	 * Render and get a response from the form for the creation of course indexes.
	 */
	public CreateIndexResponse renderCreateIndexForm(String course) {
		return createIndexForm.getResponse(gui, course);
	}

	@Override
	/**
	 * Render and get a response from the input form in text format. 
	 */
	public String getText(String title, String description) {
		return GetInputForm.getText(gui, title, description);
	}

	@Override
	/**
	 * Render and get a response from the input form in numerical format.
	 */
	public Integer getInt(String title, String description) {
		return GetInputForm.getInt(gui, title, description);
	}

	@Override
	/**
	 * Render and get a response from the form for the dialog prompt.
	 */
	public void renderDialog(String title, String msg) {
		MessageDialog.showMessageDialog(gui, title, msg);
	}

	@Override
	/**
	 * Render and get a response from the form for the administrator menu.
	 */
	public AdminMenuResponse renderAdminMenuForm() {
		return adminMenuForm.getResponse(gui);
	}

	@Override
	/**
	 * Render and get a response from the form for the changing of access period.
	 */
	public AccessPeriodResponse renderAccessPeriodForm(String curAccessPeriod) {
		return accessPeriodForm.getResponse(gui, curAccessPeriod);
	}

	@Override
	/**
	 * Render and get a response from the form for management of courses.
	 */
	public CourseManagementResponse renderCourseManagementForm(List<String> courses) {
		return courseManagementForm.getResponse(gui, courses);
	}

	@Override
	/**
	 * Render and get a response from the form for the management of course indexes.
	 */
	public IndexManagementResponse renderIndexManagementForm(String courseCode, List<String> indexes) {
		return indexManagementForm.getResponse(gui, courseCode, indexes);
	}

	@Override
	/**
	 * Render and get a response from the form for the creation of lessons.
	 */
	public CreateLessonResponse renderCreateLessonForm(String index, List<String> lessonType, List<String> days) {
		return createLessonForm.getResponse(gui, index, lessonType, days);
	}

	@Override
	/**
	 * Render and get a response from the form for displaying the list of students.
	 */
	public void renderStudentList(String title, List<Student> students) {
		DisplayList<Student> display = new DisplayList<>();

		display.show(gui, title, "No students", new String[] { "Name", "Gender", "Nationality" }, students,
				(s) -> new String[] { s.getName(), s.getGender().toString(), s.getNationality().toString() });
	}

	@Override
	/**
	 * Render and get a response from the form for displaying the list of courses.
	 */
	public void renderCourseList(String title, List<Course> courses) {
		DisplayList<Course> display = new DisplayList<>();

		display.show(gui, title, "No courses", new String[] { "Course Code", "Name", "School", "AU" }, courses,
				(c) -> new String[] { c.getCourseCode(), c.getName(), c.getSchool().toString(),
						Integer.toString(c.getAu()) });
	}

	@Override
	/**
	 * Render and get a response from the form for displaying the confirmation prompt.
	 */
	public boolean renderConfirmation(String title, String msg) {
		return new MessageDialogBuilder().setTitle(title).setText(msg).addButton(MessageDialogButton.Cancel)
				.addButton(MessageDialogButton.OK).build().showDialog(gui) == MessageDialogButton.OK;
	}

	@Override
	/**
	 * Render and get a response from the form for displaying index information.
	 */
	public void renderIndexInfo(String title, List<Index> indexes) {
		DisplayIndexList.show(gui, title, indexes);
	}

	@Override
	/**
	 * Render and get a response from the form for displaying the confirmation to change index.
	 */
	public boolean renderIndexChangeConfirmation(String title, String description, String labelA, Index indexA,
			String labelB, Index indexB) {
		return indexChangeConfirmationForm.confirm(gui, title, description, labelA, indexA, labelB, indexB);
	}

	@Override
	/**
	 * Render and get a response from the form for displaying the list of registered courses.
	 */
	public void renderRegisteredCourses(String title, List<Registration> regs) {
		DisplayRegisteredCourses.display(gui, title, regs);
	}
}

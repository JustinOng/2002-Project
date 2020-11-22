package mystars.forms;

import java.util.List;
import java.util.Map;

import mystars.entities.Registration;

/**
 * <h1>Interface: IUserInterface</h1>
 * 
 * This interface manages the respective forms for the operation of MySTARS.
 */
public interface IUserInterface {
	/**
	 * Displays login form
	 * 
	 * @return {@code LoginResponse} containing user input, or {@code null} if the
	 *         form is closed without any input
	 */
	public LoginResponse renderLoginForm();

	/**
	 * Displays list of options that a student can perform to manage courses. {@code
	 * courses} is also displayed on the form. If {@code courses} is empty (ie the
	 * student is not in any courses), the only option displayed is to register for
	 * a course.
	 * 
	 * @param courses List of courses that the student is currently in.
	 * @return Selected option, or {@code null} if the form is closed without any
	 *         input
	 */
	public StudentMenuResponse renderStudentMenuForm(List<String> courses);

	/**
	 * Displays admin menu with list of things the admin can do
	 * 
	 * @return Selected option, or {@code null} if the form is closed without any
	 *         input
	 */
	public AdminMenuResponse renderAdminMenuForm();

	/**
	 * Displays form requesting user input for swopping indexes
	 * 
	 * @return User input, or {@code null} if the form is cancelled/closed without
	 *         any input
	 */
	public IndexSwopResponse renderIndexSwopForm();

	/**
	 * Displays a generic form requesting that the user select one of {@code items}
	 * 
	 * @param title Title to label form with
	 * @param items List of items that can be selected
	 * @return Selected item, or {@code null} if the form is cancelled/closed
	 *         without any input
	 */
	public TextResponse renderItemSelectorForm(String title, List<String> items);

	/**
	 * Displays form requesting user input for creating a student
	 * 
	 * @param genders       The list of genders that a student can assume
	 * @param nationalities The list of nationalities that a student can atssume
	 * @return User input to be used to create a new Student, or {@code null} if the
	 *         form is cancelled/closed without any input
	 */
	public CreateStudentResponse renderCreateStudentForm(List<String> genders, List<String> nationalities);

	/**
	 * Displays form requesting user input for creating a course
	 * 
	 * @param schools The list of schools that a course can belong to
	 * @return User input to be used to create a new Course, or {@code null} if the
	 *         form is cancelled/closed without any input
	 */
	public CreateCourseResponse renderCreateCourseForm(List<String> schools);

	/**
	 * Displays form requesting user input for creating a course index
	 * 
	 * @param course Parent course name to be displayed on the form
	 * @return User input to be used to create a new Index, or {@code null} if the
	 *         form is cancelled/closed without any input
	 */
	public CreateIndexResponse renderCreateIndexForm(String course);

	/**
	 * Displays form requesting user input for creating a lesson
	 * 
	 * @param index      Parent index number to be displayed on the form
	 * @param lessonType The list of lesson types that the lesson can take.
	 * @param days       The list of days that the lesson can be conducted on.
	 * @return User input to be used to create a new Lesson, or {@code null} if the
	 *         form is cancelled/closed without any input
	 */
	public CreateLessonResponse renderCreateLessonForm(String index, List<String> lessonType, List<String> days);

	/**
	 * Displays form requesting user input for setting the access period
	 * 
	 * @param curAccessPeriod Current access period to be displayed on the form
	 * @return User input containing new access period, or {@code null} if the form
	 *         is cancelled/closed without any input
	 */
	public AccessPeriodResponse renderAccessPeriodForm(String curAccessPeriod);

	/**
	 * Displays form with list of {@code courses} and request for the user to select
	 * an action to perform on a course
	 * 
	 * @param courses List of courses that can be managed
	 * @return Selected option, or {@code null} if the form is cancelled/closed
	 *         without any input
	 */
	public CourseManagementResponse renderCourseManagementForm(List<String> courses);

	/**
	 * Displays form with list of {@code indexes} and request for the user to select
	 * an action to perform on an index
	 * 
	 * @param courseCode Course code to display in the form title
	 * @param indexes    List of indexes that can be managed
	 * @return Selected option, or {@code null} if the form is cancelled/closed
	 *         without any input
	 */
	public IndexManagementResponse renderIndexManagementForm(String courseCode, List<String> indexes);

	/**
	 * Displays form requesting for text input from the user
	 * 
	 * @param title       The form title
	 * @param description The form description
	 * @return User input, or {@code null} if the form is cancelled/closed without
	 *         any input
	 */
	public String getText(String title, String description);

	/**
	 * Displays form requesting for integer input from the user
	 * 
	 * @param title       The form title.
	 * @param description The form description.
	 * @return User input, or {@code null} if the form is cancelled/closed without
	 *         any input
	 */
	public Integer getInt(String title, String description);

	/**
	 * Displays dialog with specified title and message
	 * 
	 * @param title The dialog title.
	 * @param msg   The dialog message.
	 */
	public void renderDialog(String title, String msg);

	/**
	 * Displays dialog with specified title and message requesting user confirmation
	 * 
	 * @param title The dialog title.
	 * @param msg   The dialog message.
	 * @return {@code true} if the user confirmed, or {@code false} otherwise
	 */
	public boolean renderConfirmation(String title, String msg);

	/**
	 * Displays a list of students with the given title
	 * 
	 * @param title    The title of the form.
	 * @param students The list of students. List of string arrays, ie { {"name",
	 *                 "gender", "nationality}, ...}
	 */
	public void renderStudentList(String title, List<String[]> students);

	/**
	 * Displays a list of indexes with the given title
	 * 
	 * @param title   The title of the form.
	 * @param indexes Map of index number to index info, where index info is a list
	 *                of string arrays containing {"Class Type", "Group", "Day",
	 *                "Time", "Venue"}
	 */
	public void renderIndexInfo(String title, Map<String, List<String[]>> indexes);

	/**
	 * Display detailed information about two indexes and request for the user to confirm an action
	 * 
	 * @param title Title of the form
	 * @param description Description to be shown on the form
	 * @param labelA text to label {@code lessonsA} with
	 * @param lessonsA lessons from the first index
	 * @param labelB text to label {@code lessonsB} with
	 * @param lessonsB lessons from the second index
	 * @return {@code true} if the user confirmed the action, or {@code false} otherwise
	 */
	public boolean renderIndexChangeConfirmation(String title, String description, String labelA,
			List<String[]> lessonsA, String labelB, List<String[]> lessonsB);
	
	/**
	 * Display a list of courses and their individual lessons
	 * 
	 * @param title Title of the form
	 * @param regs List of Registrations to display
	 */
	public void renderRegisteredCourses(String title, List<Registration> regs);
}

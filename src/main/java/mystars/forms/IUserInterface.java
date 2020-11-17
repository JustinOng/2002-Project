package mystars.forms;

import java.util.List;

/**
 * <h1>Interface: IUserInterface</h1>
 * 
 * This interface manages the respective forms for the operation of MySTARS.
 */
public interface IUserInterface {
	/**
	 * Creates and returns the login response form.
	 * 
	 * @return The login response form.
	 */
	public LoginResponse renderLoginForm();
	
	/**
	 * Creates and returns the student menu form.
	 * 
	 * @param courses The list of courses available to students.
	 * @return The student menu form.
	 */
	public StudentMenuResponse renderStudentMenuForm(List<String> courses);
	
	/**
	 * Creates and returns the admin menu form.
	 * 
	 * @return The admin menu form.
	 */
	public AdminMenuResponse renderAdminMenuForm();
	
	/**
	 * Creates and returns the course index swop form.
	 * 
	 * @return The course index swop form.
	 */
	public IndexSwopResponse renderIndexSwopForm();
	
	/**
	 * Creates and returns the ite selector form.
	 * 
	 * @param title	The title of the form.
	 * @param items	The list of items in the form.
	 * @return 		The item selector form.
	 */
	public TextResponse renderItemSelectorForm(String title, List<String> items);
	
	/**
	 * Creates and returns the form to create students.
	 * 
	 * @param genders 		The list of genders that a student can assume.
	 * @param nationalities	The list of nationalities that a student can assume.
	 * @return 				The form to create students.
	 */
	public CreateStudentResponse renderCreateStudentForm(List<String> genders, List<String> nationalities);
	
	/**
	 * Creates and returns the form to create courses.
	 * 
	 * @param schools 	The list of schools that a course can belong to.
	 * @return 			The form to create courses.
	 */
	public CreateCourseResponse renderCreateCourseForm(List<String> schools);
	
	/**
	 * Creates and returns the form to create course indexes.
	 * 
	 * @param course	The list of courses that the index can come from.
	 * @return 			The form to create course indexes.
	 */
	public CreateIndexResponse renderCreateIndexForm(String course);
	
	/**
	 * Creates and returns the form to create course index lessons.
	 * 
	 * @param index			The list of indexes that the lesson can come from.
	 * @param lessonType	The list of lesson types that the lesson can take.
	 * @param days			The list of days that the lesson will be conducted on.
	 * @return 				The form to create course index lessons.
	 */
	public CreateLessonResponse renderCreateLessonForm(String index, List<String> lessonType, List<String> days);
	
	/**
	 * Creates and returns the form for setting the access period.
	 * 
	 * @param curAccessPeriod	The current access period.
	 * @return					The form for setting the access period.
	 */
	public AccessPeriodResponse renderAccessPeriodForm(String curAccessPeriod);
	
	/**
	 * Creates and returns the form for managing courses.
	 * 
	 * @param courses	The list of courses that can be managed.
	 * @return			The form for managing courses.
	 */
	public CourseManagementResponse renderCourseManagementForm(List<String> courses);
	
	/**
	 * Creates and returns the form for managing course indexes.
	 * 
	 * @param courseCode	The list of course codes that the index can come from.
	 * @param indexes		The list of indexes.
	 * @return				The form for managing course indexes.
	 */
	public IndexManagementResponse renderIndexManagementForm(String courseCode, List<String> indexes);
	
	/**
	 * Creates and returns the form text used for the user interface.
	 * 
	 * @param title			The form title.
	 * @param description	The form description.
	 * @return				The form text used for the user interface.
	 */
	public String getText(String title, String description);
	
	/**
	 * Returns the integer object matching the description.
	 * 
	 * @param title			The form title.
	 * @param description	The for description.
	 * @return				The integer object matching the description.
	 */
	public Integer getInt(String title, String description);
	
	/**
	 * Creates the dialog used for the user interface.
	 * 
	 * @param title	The dialog title.
	 * @param msg	The dialog message.
	 */
	public void renderDialog(String title, String msg);
	
	/**
	 * Creates the form for the list of students.
	 * 
	 * @param title		The title of the form.
	 * @param students	The list of students.
	 */
	public void renderStudentList(String title, List<String[]> students);
}

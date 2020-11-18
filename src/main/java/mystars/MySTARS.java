package mystars;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import mystars.entities.*;
import mystars.enums.*;
import mystars.exceptions.*;
import mystars.controllers.*;
import mystars.forms.*;
import mystars.notifications.INotifyStudent;

/**
 * <h1>Class: MySTARS</h1>
 * 
 * This class manages the overall operation of the MySTARS program.
 */
public class MySTARS {
	/**
	 * UI that will be used
	 */
	private IUserInterface ui;

	/**
	 * Notification method that will be used to notify students
	 */
	private INotifyStudent notifier;

	/**
	 * Create a new storage controller object to handle data persistence
	 */
	private StorageController storageController = new StorageController("data");

	/**
	 * Create a new user controller object to handle user-related operations
	 */
	private UserController userController = new UserController();

	/**
	 * Create a new course controller object to handle course/index/lesson related
	 * operations
	 */
	private CourseController courseController = new CourseController();

	/**
	 * Currently logged in user.
	 */
	private User user;

	/**
	 * Creates new instance of MySTARS with UI and notification mechanism to use
	 * 
	 * @param ui     UI to be used to interact with user
	 * @param notifier Instance to use to notify students
	 */
	public MySTARS(IUserInterface ui, INotifyStudent notifier) {
		this.ui = ui;
		this.notifier = notifier;
	}

	/**
	 * Calls the created controller and its related objects declared above to start
	 * the MySTARS program. Prompts the user to login with their credentials through
	 * the user interface's login form.
	 */
	public void start() {
		storageController.start();
		courseController.start();

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				storageController.writeToDisk();
			}
		});
		
		if (notifier != null) {
			courseController.registerIndexObserver((Index index, Student student) -> {
				notifier.notify(student, "Allocation of waitlisted Index", String.format("You have been allocated your waitlisted index %d ", index.getIndexNo()));
			}, Index.Event.AllocatedWaitlist);
		}

		try {
			// Create new administrator and student objects.
			new Admin("admin", "1");
			new Student("user1", "starsnotifications2021s1+user1@gmail.com", "u12345", "1", "1", Gender.Male,
					Nationality.Singaporean);
			new Student("user2", "starsnotifications2021s1+user2@gmail.com", "u12345", "2", "2", Gender.Male,
					Nationality.Singaporean);

			// Create new courses and indexes.
			Course c = new Course("Course", "C1", School.CSE);
			c.createIndex(1, 1);
			c.createIndex(2, 1);

//			courseController.registerCourse(s, "C1", 1);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			while (true) {
				LoginResponse response = ui.renderLoginForm();

				if (response == null)
					continue;
				try {
					// Login the user after getting their respective and correct credentials.
					user = userController.login(response.getUsername(), response.getPassword());
					break;
				} catch (AppException e) {
					ui.renderDialog("Login Failed", e.getMessage());
				}
			}

			// check if the user is either an instance of student or administrators.
			if (userController.isStudent(user)) {
				loopStudent();
			} else if (userController.isAdmin(user)) {
				loopAdmin();
			}
		} catch (UIException e) {

		}
	}

	/**
	 * Manages the actions that can be performed by a student. Actions include:
	 * Register for course, drop course, change index, swop index.
	 */
	private void loopStudent() {
		Student student = (Student) user;
		TextResponse textResponse;
		HashMap<String, Integer> indexInfo = new HashMap<>();

		while (true) {
			HashMap<String, String> registeredInfo = new HashMap<>();

			// List the course registration information for the student.
			// Includes which courses and indexes he/she has registered for.
			for (Index index : courseController.getStudentIndexes(student)) {
				registeredInfo.put(String.format("%s: %s", index.getCourse(), index),
						index.getCourse().getCourseCode());
			}
			StudentMenuResponse response = ui.renderStudentMenuForm(new ArrayList<String>(registeredInfo.keySet()));

			if (response == null) {
				continue;
			}

			try {
				switch (response.getSelected()) {

				// Register for course.
				case Register:
					Integer indexNo = ui.getInt("Register for Index", "Index No:");

					if (indexNo == null)
						break;

					// If there are still vacancies and the registration was successful.
					if (courseController.registerCourse(student, indexNo)) {
						ui.renderDialog("Registration",
								String.format("You have successfully registered for Index %d", indexNo));
					} else {
						ui.renderDialog("Registration",
								String.format("You have been placed on the waitlist for Index %d", indexNo));
					}
					break;

				// Drop course.
				case Drop:
					textResponse = ui.renderItemSelectorForm("Drop Course",
							new ArrayList<String>(registeredInfo.keySet()));

					if (textResponse == null)
						break;

					courseController.dropCourse(student, registeredInfo.get(textResponse.getText()));
					break;

				// Change course index.
				case Change:
					textResponse = ui.renderItemSelectorForm("Select Course to Change Index",
							new ArrayList<String>(registeredInfo.keySet()));

					if (textResponse == null)
						break;

					String courseCode = registeredInfo.get(textResponse.getText());

					for (Index index : courseController.getCourseIndexes(courseCode)) {
						indexInfo.put(index.toString(), index.getIndexNo());
					}
					textResponse = ui.renderItemSelectorForm("Select New Index",
							new ArrayList<String>(indexInfo.keySet()));

					if (textResponse == null)
						break;

					courseController.changeIndex(courseCode, student, indexInfo.get(textResponse.getText()));
					break;

				// Swop course index with another student.
				case Swop:
					IndexSwopResponse isResponse = ui.renderIndexSwopForm();

					if (isResponse == null)
						break;

					// Get the login credentials of the peer student whose index is to be swapped
					// with.
					User targetUser = userController.login(isResponse.getUsername(), isResponse.getPassword());
					if (!userController.isStudent(targetUser)) {
						throw new AppException("Invalid peer");
					}
					courseController.swopIndex(student, isResponse.getIndexA(), (Student) targetUser,
							isResponse.getIndexB());
					break;
				}
			} catch (AppException e) {
				ui.renderDialog("Error", e.getMessage());
			}
		}
	}

	/**
	 * Manages the actions that can be performed by administrators. Actions include:
	 * Edit student access period, create student, create course, manage course,
	 * list vacancies.
	 */
	private void loopAdmin() {
		while (true) {
			AdminMenuResponse response = ui.renderAdminMenuForm();

			if (response == null) {
				continue;
			}

			try {
				switch (response.getSelected()) {

				// Edit the access period for MySTARS.
				case EditStudentAccessPeriod:
					AccessPeriodResponse accessResponse = ui
							.renderAccessPeriodForm(userController.getStudentAccessPeriod());

					if (accessResponse == null) {
						continue;
					}

					userController.setStudentAccessPeriod(accessResponse.getStart(), accessResponse.getEnd());

					ui.renderDialog("Student Access Period", "Changed successfully");
					break;

				// Create a new student with all of that student's required information.
				case CreateStudent:
					// https://stackoverflow.com/a/29465971
					List<String> genders = Stream.of(Gender.values()).map(Enum::name).collect(Collectors.toList());
					List<String> nationalities = Stream.of(Nationality.values()).map(Enum::name)
							.collect(Collectors.toList());
					CreateStudentResponse studentResponse = ui.renderCreateStudentForm(genders, nationalities);

					if (studentResponse == null) {
						continue;
					}

					Gender gender = Gender.valueOf(studentResponse.getGender());
					Nationality nationality = Nationality.valueOf(studentResponse.getNationality());

					userController.createStudent(studentResponse.getName(), studentResponse.getEmail(),
							studentResponse.getMatricNo(), studentResponse.getUsername(), studentResponse.getPassword(),
							gender, nationality);

					ui.renderDialog("Student Creation", "Student created successfully");
					break;

				// Create a new course.
				case CreateCourse:
					List<String> schools = Stream.of(School.values()).map(Enum::name).collect(Collectors.toList());
					CreateCourseResponse courseResponse = ui.renderCreateCourseForm(schools);

					if (courseResponse == null) {
						continue;
					}

					courseController.createCourse(courseResponse.getName(), courseResponse.getCode(),
							School.valueOf(courseResponse.getSchool()));

					ui.renderDialog("Course Creation", "Course created successfully");
					break;

				// Manage an existing course.
				case ManageCourses:
					loopCourseMangement();
					break;

				// List the number of vacancies in a course index.
				case ListVacancies:
					int indexNo = ui.getInt("List vacancies", "Index No:");
					ui.renderDialog(String.format("Index %d", indexNo), String.format("%d/%d vacancies/max enrolled",
							courseController.getVacancies(indexNo), courseController.getMaxEnrolled(indexNo)));
					break;
				case ListStudents:
					displayStudents("All Students", userController.getAllStudents());
				default:
					break;
				}
			} catch (AppException e) {
				ui.renderDialog("Error", e.getMessage());
			}
		}
	}

	/**
	 * Manages the actions that can be performed on a course. Actions include:
	 * Create index, list students in an index, manage index.
	 */
	private void loopCourseMangement() {
		while (true) {
			HashMap<String, String> courses = new HashMap<String, String>();

			// Display information about the selected course.
			for (Course course : courseController.getAllCourses()) {
				courses.put(course.toString(), course.getCourseCode());
			}
			CourseManagementResponse courseMgmtResponse = ui
					.renderCourseManagementForm(new ArrayList<String>(courses.keySet()));

			if (courseMgmtResponse == null) {
				break;
			}

			String courseCode = courses.get(courseMgmtResponse.getCourseName());

			try {
				switch (courseMgmtResponse.getSelected()) {

				// Create a new index for the course.
				case CreateIndex:
					CreateIndexResponse indexResponse = ui.renderCreateIndexForm(courseCode);

					if (indexResponse == null) {
						continue;
					}

					courseController.createIndex(courseCode, indexResponse.getNumber(), indexResponse.getMaxEnrolled());

					ui.renderDialog("Index Creation", "Index created successfully");
					break;

				// List all the students registered for a course index.
				case ListStudents:
					displayStudents("Students registered for " + courseCode,
							courseController.getStudentsByCourse(courseCode));
					break;

				// Manage a course's indexes.
				case ManageIndexes:
					loopIndexManagement(courseCode);
					break;
				default:
					break;
				}
				break;
			} catch (AppException e) {
				ui.renderDialog("Error", e.getMessage());
			}
		}
	}

	/**
	 * Manages the actions that can be performed on a course index. Actions include:
	 * Create lesson, list students.
	 * 
	 * @param courseCode Specifies the course on which we will perform index
	 *                   management
	 */
	private void loopIndexManagement(String courseCode) {
		while (true) {
			HashMap<String, Integer> indexes = new HashMap<String, Integer>();

			// Display all the indexes of the selected course.
			try {
				for (Index index : courseController.getAllIndexes(courseCode)) {
					indexes.put(index.toString(), index.getIndexNo());
				}
			} catch (AppException e) {
				ui.renderDialog("Error", e.getMessage());
				return;
			}
			IndexManagementResponse indexMgmtResponse = ui.renderIndexManagementForm(courseCode,
					new ArrayList<String>(indexes.keySet()));

			if (indexMgmtResponse == null) {
				break;
			}
			int indexNo = indexes.get(indexMgmtResponse.getIndex());

			try {
				switch (indexMgmtResponse.getSelected()) {

				// Create a new index lesson.
				case CreateLesson:
					List<String> lessonTypes = Stream.of(LessonType.values()).map(Enum::name)
							.collect(Collectors.toList());
					List<String> days = Stream.of(Day.values()).map(Enum::name).collect(Collectors.toList());
					CreateLessonResponse lessonResponse = ui.renderCreateLessonForm(String.format("Index %d", indexNo),
							lessonTypes, days);

					if (lessonResponse == null) {
						continue;
					}
					LessonType lessonType = LessonType.valueOf(lessonResponse.getLessonType());
					Day day = Day.valueOf(lessonResponse.getDay());

					courseController.createLesson(courseCode, indexNo, lessonType, day, lessonResponse.getLocation(),
							lessonResponse.getGroupNo(), lessonResponse.getWeeks(), lessonResponse.getStartPeriod(),
							lessonResponse.getEndPeriod());
					break;

				// List all the students registered for an index.
				case ListStudents:
					displayStudents(String.format("Students registered for Index %d", indexNo),
							courseController.getStudentsByIndex(indexNo));
					break;
				default:
					break;
				}
			} catch (AppException e) {
				ui.renderDialog("Error", e.getMessage());
			}
		}
	}

	/**
	 * Displays a titled list of all the students and their information
	 * 
	 * @param title    Title to use when displaying the list
	 * @param students The list of students.
	 */
	private void displayStudents(String title, List<Student> students) {
		List<String[]> data = new ArrayList<>();

		// For each student, get and display their information.
		for (Student s : students) {
			data.add(new String[] { s.getName(), s.getGender().toString(), s.getNationality().toString() });
		}
		ui.renderStudentList(title, data);
	}
}

package mystars;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import mystars.entities.*;
import mystars.enums.*;
import mystars.exceptions.*;
import mystars.controllers.*;
import mystars.forms.*;

public class MySTARS {
	private IUserInterface ui;
	private StorageController storageController = new StorageController("data");
	private UserController userController = new UserController();
	private CourseController courseController = new CourseController();

	private User user;

	public MySTARS(IUserInterface ui) throws IOException {
		this.ui = ui;
	}

	public void start() {
		storageController.start();
		courseController.start();

		try {
			new Admin("admin", "1");
			new Student("user1", "u12345", "1", "1", Gender.Male, Nationality.Singaporean);
			new Student("user2", "u12345", "2", "2", Gender.Male, Nationality.Singaporean);

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
					user = userController.login(response.getUsername(), response.getPassword());
					break;
				} catch (AppException e) {
					ui.renderDialog("Login Failed", e.getMessage());
				}
			}

			if (userController.isStudent(user)) {
				loopStudent();
			} else if (userController.isAdmin(user)) {
				loopAdmin();
			}
		} catch (UIException e) {

		}
	}

	private void loopStudent() {
		Student student = (Student) user;
		TextResponse textResponse;
		HashMap<String, Integer> indexInfo = new HashMap<>();

		while (true) {
			HashMap<String, String> registeredInfo = new HashMap<>();
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
				case Register:
					Integer indexNo = ui.getInt("Register for Index", "Index No:");

					if (indexNo == null)
						break;

					if (courseController.registerCourse(student, indexNo)) {
						ui.renderDialog("Registration",
								String.format("You have successfully registered for Index %d", indexNo));
					} else {
						ui.renderDialog("Registration",
								String.format("You have been placed on the waitlist for Index %d", indexNo));
					}
					break;
				case Drop:
					textResponse = ui.renderItemSelectorForm("Drop Course",
							new ArrayList<String>(registeredInfo.keySet()));

					if (textResponse == null)
						break;

					courseController.dropCourse(student, registeredInfo.get(textResponse.getText()));
					break;
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
				case Swop:
					IndexSwopResponse isResponse = ui.renderIndexSwopForm();

					if (isResponse == null)
						break;

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

	private void loopAdmin() {
		while (true) {
			AdminMenuResponse response = ui.renderAdminMenuForm();

			if (response == null) {
				continue;
			}

			try {
				switch (response.getSelected()) {
				case EditStudentAccessPeriod:
					AccessPeriodResponse accessResponse = ui
							.renderAccessPeriodForm(userController.getStudentAccessPeriod());

					if (accessResponse == null) {
						continue;
					}

					userController.setStudentAccessPeriod(accessResponse.getStart(), accessResponse.getEnd());

					ui.renderDialog("Student Access Period", "Changed successfully");
					break;
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

					userController.createStudent(studentResponse.getName(), studentResponse.getMatricNo(),
							studentResponse.getUsername(), studentResponse.getPassword(), gender, nationality);

					ui.renderDialog("Student Creation", "Student created successfully");
					break;
				case CreateCourse:
					List<String> schools = Stream.of(School.values()).map(Enum::name).collect(Collectors.toList());
					CreateCourseResponse courseResponse = ui.renderCreateCourseForm(schools);

					if (courseResponse == null) {
						continue;
					}

					courseController.createCourse(courseResponse.getName(), courseResponse.getCode(),
							courseResponse.getSchool());

					ui.renderDialog("Course Creation", "Course created successfully");
					break;
				case ManageCourses:
					loopCourseMangement();
					break;
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

	private void loopCourseMangement() {
		while (true) {
			HashMap<String, String> courses = new HashMap<String, String>();
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
				case CreateIndex:
					CreateIndexResponse indexResponse = ui.renderCreateIndexForm(courseCode);

					if (indexResponse == null) {
						continue;
					}

					courseController.createIndex(courseCode, indexResponse.getNumber(), indexResponse.getMaxEnrolled());

					ui.renderDialog("Index Creation", "Index created successfully");
					break;
				case ListStudents:
					displayStudents("Students registered for " + courseCode,
							courseController.getStudentsByCourse(courseCode));
					break;
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

	private void loopIndexManagement(String courseCode) {
		while (true) {
			HashMap<String, Integer> indexes = new HashMap<String, Integer>();
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

	private void displayStudents(String title, List<Student> students) {
		List<String[]> data = new ArrayList<>();
		for (Student s : students) {
			data.add(new String[] { s.getName(), s.getGender().toString(), s.getNationality().toString() });
		}

		ui.renderStudentList(title, data);
	}
}

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
		try {
			new Admin("admin", "1");
			new Student("user", "u12345", "1", "1", Gender.Male, Nationality.Singaporean);
			new Student("user", "u12345", "2", "2", Gender.Male, Nationality.Singaporean);

			Course c = new Course("Course", "C1", School.CSE);
			c.createIndex(1, 5);
			c.createIndex(2, 5);

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
		HashMap<String, Integer> indexInfo;

		while (true) {
			HashMap<String, String> registeredInfo = courseController.getRegisteredInfo(student);
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

					courseController.registerCourse(student, indexNo);
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

					indexInfo = courseController.getIndexInfo(courseCode);

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
				case CheckIndexVacancies:
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
					
					courseController.createCourse(courseResponse.getName(), courseResponse.getCode(), courseResponse.getSchool());

					ui.renderDialog("Course Creation", "Course created successfully");
					break;
				case ListStudentsByCourse:
					break;
				case ListStudentsByIndex:
					break;
				default:
					break;
				}
			} catch (AppException e) {
				ui.renderDialog("Error", e.getMessage());
			}
		}
	}
}

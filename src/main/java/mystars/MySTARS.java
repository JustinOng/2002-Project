package mystars;

import java.io.IOException;
import java.util.*;

import mystars.entities.*;
import mystars.enums.*;
import mystars.exceptions.AppException;
import mystars.controllers.*;
import mystars.forms.*;

public class MySTARS {
	private IUserInterface ui;
	private UserController userController = new UserController();
	private CourseController courseController = new CourseController();

	public MySTARS(IUserInterface ui) throws IOException {
		this.ui = ui;
	}

	public void start() {
		try {
			Student s = new Student("user", "u12345", "user", "pass", Gender.Male, Nationality.Singaporean);

			Course c = new Course("Course", "C1", School.CSE);
			c.createIndex(1, 5);

//			courseController.registerCourse(s, "C1", 1);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		String msg = "";
		while (true) {
			LoginResponse response = ui.renderLoginForm(msg);
			try {
				userController.login(response.getUsername(), response.getPassword());
				break;
			} catch (Exception e) {
				msg = "Invalid login.";
			}
		}

		if (userController.isLoggedInStudent()) {
			loopStudent();
		}
	}

	private void loopStudent() {
		Student student = userController.getStudent();

		while (true) {
			ArrayList<String> registeredCourses = new ArrayList<String>();

			for (Course c : student.getRegisteredCourses()) {
				registeredCourses.add(c.toString());
			}

			StudentMenuResponse response = ui.renderStudentMenuForm(registeredCourses);

			switch (response.getSelected()) {
			case Register:
				int indexNo = ui.getInt("Register for Index", "Index No:");
				
				try {
					courseController.registerCourse(student, indexNo);
				} catch (AppException e) {
					ui.renderDialog("Failed to add Index", e.getMessage());
				}
				break;
			case Drop:
				break;
			case Change:
				break;
			case Swop:
				break;
			}
		}
	}
}

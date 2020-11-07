package mystars;

import java.io.IOException;
import java.util.*;

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

	public MySTARS(IUserInterface ui) throws IOException {
		this.ui = ui;
	}

	public void start() {
		try {
			Student s = new Student("user", "u12345", "user", "pass", Gender.Male, Nationality.Singaporean);

			Course c = new Course("Course", "C1", School.CSE);
			c.createIndex(1, 5);
			c.createIndex(2, 5);

//			courseController.registerCourse(s, "C1", 1);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			String msg = "";
			while (true) {
				LoginResponse response = ui.renderLoginForm(msg);

				if (response == null)
					continue;
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
		} catch (UIException e) {

		}
	}

	private void loopStudent() {
		Student student = userController.getStudent();
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
					break;
				}
			} catch (AppException e) {
				ui.renderDialog("Error", e.getMessage());
			}
		}
	}
}

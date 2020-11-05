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
	private UserController userController = new UserController();

	public MySTARS(IUserInterface ui) throws IOException {
		this.ui = ui;
	}

	public void start() {
		try {
			new Student("user", "u12345", "user", "pass", Gender.Male, Nationality.Singaporean);
		} catch (UserAlreadyExistsException e1) {
		}

		String msg = "";
		while (true) {
			LoginResponse response = ui.renderLoginForm(msg);
			try {
				userController.login(response.getUsername(), response.getPassword());
				break;
			} catch (InvalidLoginException e) {
				msg = "Invalid login.";
			}
		}

		if (userController.isLoggedInStudent()) {
			loopStudent();
		}
	}
	
	private void loopStudent() {
		while (true) {
			StudentMenuResponse response = ui.renderStudentMenuForm(Arrays.asList("course A"));
			
			System.out.println(response.getSelected().name());
		}
	}
}

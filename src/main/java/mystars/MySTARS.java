package mystars;

import java.io.IOException;
import java.util.*;

import mystars.entities.*;
import mystars.enums.*;
import mystars.exceptions.*;
import mystars.controllers.*;
import mystars.forms.*;
import mystars.forms.cgui.ConsoleGraphicUserInterface;

public class MySTARS {
	private IUserInterface ui = new ConsoleGraphicUserInterface();
	private UserController userController = new UserController();

	public MySTARS() throws IOException {
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
	}
}

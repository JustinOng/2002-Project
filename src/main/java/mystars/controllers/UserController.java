package mystars.controllers;

import mystars.entities.*;
import mystars.enums.*;
import mystars.exceptions.*;

/**
 * <h1>Class: userController</h1>
 * 
 * This userController class manages the creation of a new student, and the
 * logins of students or administrators.
 * 
 */

public class UserController {
	public UserController() {

	}

	/**
	 * This method creates a new student object.
	 * 
	 * @param name        The student's name.
	 * @param matricNo    The student's matriculation number.
	 * @param userName    The student's username.
	 * @param password    The student's password.
	 * @param gender      The student's gender.
	 * @param nationality The student's nationality.
	 * @throws UserAlreadyExistsException 
	 */
	public void createStudent(String name, String matricNo, String userName, String password, Gender gender,
			Nationality nationality) throws UserAlreadyExistsException {
		Student mystudent = new Student(name, matricNo, userName, password, gender, nationality);
	}

	/**
	 * This method manages the user's login.
	 * 
	 * @param userName The user's username.
	 * @param password The user's password.
	 */
	public void login(String userName, String password) {

	}
}

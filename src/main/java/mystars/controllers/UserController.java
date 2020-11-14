package mystars.controllers;

import mystars.entities.*;
import mystars.enums.*;
import mystars.exceptions.AppException;

/**
 * <h1>Class: UserController</h1>
 * 
 * This userController class manages the creation of a new student, and the logins of students or administrators.
 */

public class UserController {	
	/**
	 * 
	 */
	public UserController() {

	}

	/**
	 * Create a new student object.
	 * 
	 * @param name        The student's name.
	 * @param matricNo    The student's matriculation number.
	 * @param username    The student's username.
	 * @param password    The student's password.
	 * @param gender      The student's gender.
	 * @param nationality The student's nationality.
	 * @throws UserAlreadyExistsException If there already exists a user with the same username.
	 */
	public void createStudent(String name, String matricNo, String userName, String password, Gender gender,
			Nationality nationality) throws Exception {
		Student mystudent = new Student(name, matricNo, userName, password, gender, nationality);
	}

	/**
	 * Manage the user's login.
	 * 
	 * @param username The user's username.
	 * @param password The user's password.
	 * @throws InvalidLoginException If the login is unsuccessful due to incorrect username or password.
	 * @return The user object of the user successfully logging in.
	 */
	public User login(String username, String password) throws AppException {
		return User.login(username, password);
	}
	
	/**
	 * Check if the user is a student.
	 * @param user 	The student object.
	 * @return		Boolean value indicating if the user is an instance of student.
	 */
	public boolean isStudent(User user) {
		return user instanceof Student;
	}
	
	/**
	 * Check if the user is an admin.
	 * 
	 * @param user 	The admin object.
	 * @return		Boolean value indicating if the user is an instance of admin.
	 */
	public boolean isAdmin(User user) {
		return user instanceof Admin;
	}
}

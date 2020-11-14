package mystars.controllers;

import mystars.entities.*;
import mystars.enums.*;
import mystars.exceptions.AppException;

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
	 * @param username    The student's username.
	 * @param password    The student's password.
	 * @param gender      The student's gender.
	 * @param nationality The student's nationality.
	 * @throws UserAlreadyExistsException 
	 */
	public void createStudent(String name, String matricNo, String userName, String password, Gender gender,
			Nationality nationality) throws AppException {
		new Student(name, matricNo, userName, password, gender, nationality);
	}
	
	public void createAdmin(String username, String password) throws AppException {
		new Admin(username, password);
	}

	/**
	 * This method manages the user's login.
	 * 
	 * @param username The user's username.
	 * @param password The user's password.
	 * @throws InvalidLoginException 
	 */
	public User login(String username, String password) throws AppException {
		return User.login(username, password);
	}
	
	public boolean isStudent(User user) {
		return user instanceof Student;
	}
	
	public boolean isAdmin(User user) {
		return user instanceof Admin;
	}
}

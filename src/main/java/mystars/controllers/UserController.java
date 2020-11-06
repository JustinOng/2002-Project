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
	// should this be kept here or in MySTARS?
	private User user;
	
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
			Nationality nationality) throws UserAlreadyExistsException {
		Student mystudent = new Student(name, matricNo, userName, password, gender, nationality);
	}

	/**
	 * This method manages the user's login.
	 * 
	 * @param username The user's username.
	 * @param password The user's password.
	 * @throws InvalidLoginException 
	 */
	public void login(String username, String password) throws InvalidLoginException {
		user = User.login(username, password);
	}
	
	public boolean isLoggedInStudent() {
		return user != null && user instanceof Student;
	}
	
	public Student getStudent() {
		if (user instanceof Student) {
			return (Student) user;
		}
		
		throw new WrongUserTypeException();
	}
	
	public boolean isLoggedInAdmin() {
		return user != null && user instanceof Admin;
	}
	
	public Admin getAdmin() {
		if (user instanceof Admin) {
			return (Admin) user;
		}
		
		throw new WrongUserTypeException();
	}
}

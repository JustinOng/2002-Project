package mystars.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;

import mystars.entities.*;
import mystars.enums.*;
import mystars.exceptions.AppException;

/**
 * <h1>Class: UserController</h1>
 * 
 * This userController class manages the creation of a new student, and the
 * logins of students or administrators.
 */
public class UserController {
	/**
	 * Create a new student object
	 * 
	 * @param name        The student's name
	 * @param email       The student's email
	 * @param matricNo    The student's matriculation number
	 * @param username    The student's username
	 * @param password    The student's password
	 * @param gender      The student's gender
	 * @param nationality The student's nationality
	 * @throws AppException If there already exists a student with the same
	 *                      username
	 */
	public void createStudent(String name, String email, String matricNo, String username, String password,
			Gender gender, Nationality nationality) throws AppException {
		Student student = new Student(name, email, matricNo, username, password, gender, nationality);
		student.markPersistent();
	}

	/**
	 * Create a new administrator object
	 * 
	 * @param username The admin's username
	 * @param password The admin's password
	 * @throws AppException If the administrator object cannot be created
	 */
	public void createAdmin(String username, String password) throws AppException {
		User user = new User(username, password);
		user.setAdmin(true);
		user.markPersistent();
	}

	/**
	 * Manage the user's login
	 * 
	 * @param username The user's username
	 * @param password The user's password
	 * @throws AppException If the login is unsuccessful due to incorrect username
	 *                      or password
	 * @return The user object of the user successfully logging in
	 */
	public User login(String username, String password) throws AppException {
		return User.login(username, password);
	}

	/**
	 * Check if the user is a student
	 * 
	 * @param user User to be checked
	 * @return {@code true} if {@code user} is an student, or {@code false}
	 *         otherwise
	 */
	public boolean isStudent(User user) {
		return user instanceof Student;
	}

	/**
	 * Check if the user is an admin
	 * 
	 * @param user User to be checked
	 * @return {@code true} if {@code user} is an admin, or {@code false} otherwise
	 */
	public boolean isAdmin(User user) {
		return user.isAdmin();
	}

	/**
	 * Sets the period in which students are allowed to login
	 * 
	 * @param start start of the window in which students are allowed to login
	 * @param end   end of the window in which students are allowed to login
	 */
	public void setStudentAccessPeriod(LocalDateTime start, LocalDateTime end) {
		Student.setAccessPeriod(start, end);
	}

	/**
	 * Retrieve the period in which students are allowed to login as a string
	 * 
	 * @return "yyyy-mm-ddThh:mm:ss to yyyy-mm-ddThh:mm:ss" if a access period is
	 *         set, or {@code null} otherwise
	 */
	public String getStudentAccessPeriod() {
		return Student.getAccessPeriod();
	}

	/**
	 * Retrieves all students
	 * 
	 * @return list of all students
	 */
	public ArrayList<Student> getAllStudents() {
		return Student.getAllStudents();
	}
}

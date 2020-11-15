package mystars.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import mystars.enums.*;
import mystars.exceptions.AppException;

/**
 * <h1>Class: Student</h1>
 * 
 * This student class inherits from the user class, and is responsible for the creation of a new student object.
 */

public class Student extends User {
	/**
	 * Serialization of the course ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The name of the student.
	 */
	private String name;
	
	/**
	 * The matriculation number of the student.
	 */
	private String matricNo;

	/**
	 * Create an instance of the gender and nationality enum as defined in the package: 'mystars.enums'.
	 */
	private Gender gender;
	private Nationality nationality;

	/**
	 * Timetable object for use by the student object.
	 */
	Timetable studentTimetable = new Timetable();

	/**
	 * This class is responsible for the creation of a student object.
	 * 
	 * @param name        The student's name.
	 * @param matricNo    The student's matriculation number.
	 * @param username    The student's username.
	 * @param password    The student's password.
	 * @param gender      The student's gender.
	 * @param nationality The student's nationality.
	 * @throws UserAlreadyExistsException If there already exists a user with the same username.
	 */
	public Student(String name, String matricNo, String username, String password, Gender gender,
			Nationality nationality) throws AppException {
		super(username, password);
		this.name = name;
		this.matricNo = matricNo;
		this.gender = gender;
		this.nationality = nationality;
	}

	@Override
	public boolean login(String password) throws AppException {
		if (!canLogin()) {
			throw new AppException("User is not allowed to login right now");
		}

		return super.login(password);
	}

	private boolean canLogin() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime accessPeriodStart = (LocalDateTime) get("student-accessperiod", "start");
		LocalDateTime accessPeriodEnd = (LocalDateTime) get("student-accessperiod", "end");

		if (accessPeriodStart == null || accessPeriodEnd == null)
			return false;

		return (now.isAfter(accessPeriodStart) && now.isBefore(accessPeriodEnd));
	}

	public static void setAccessPeriod(LocalDateTime start, LocalDateTime end) {
		store("student-accessperiod", "start", start);
		store("student-accessperiod", "end", end);
	}

	public static String getAccessPeriod() {
		LocalDateTime accessPeriodStart = (LocalDateTime) get("student-accessperiod", "start");
		LocalDateTime accessPeriodEnd = (LocalDateTime) get("student-accessperiod", "end");

		if (accessPeriodStart == null || accessPeriodEnd == null)
			return null;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		return String.format("%s to %s", formatter.format(accessPeriodStart), formatter.format(accessPeriodEnd));
	}

	/**
	 * Return the student's name.
	 * 
	 * @return The student's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Return the student's gender as a category from the Gender enum.
	 * 
	 * @return The student's gender.
	 */
	public Gender getGender() {
		return gender;
	}
	
	/**
	 * Returns the student's nationality as a category from the Nationality enum.
	 * 
	 * @return The student's nationality.
	 */
	public Nationality getNationality() {
		return nationality;
	}

	/**
	 * Return the student's timetable object.
	 * 
	 * @return The student's timetable object.
	 */
	public Timetable getTimetable() {
		return studentTimetable;
	}

	/**
	 * Return the student's matriculation number.
	 * 
	 * @return The student's matriculation number.
	 */
	public String getMatricNo() {
		return matricNo;
	}
	
	public Nationality getNationality() {
		return nationality;
	}
}

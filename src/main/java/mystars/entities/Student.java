package mystars.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import mystars.enums.*;
import mystars.exceptions.AppException;

/**
 * <h1>Class: Student</h1>
 * 
 * This student class inherits from the user class, and is responsible for the
 * creation of a new student object.
 */

public class Student extends User {
	/**
	 * ID for versioning of serialized data.
	 */
	private static final long serialVersionUID = 2L;

	/**
	 * The name of the student.
	 */
	private String name;

	/**
	 * The email address of the student.
	 */
	private String email;

	/**
	 * The matriculation number of the student.
	 */
	private String matricNo;

	/**
	 * Create an instance of the gender and nationality enum as defined in the
	 * package: 'mystars.enums'.
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
	 * @param email       The student's email.
	 * @param matricNo    The student's matriculation number.
	 * @param username    The student's username.
	 * @param password    The student's password.
	 * @param gender      The student's gender.
	 * @param nationality The student's nationality.
	 * @throws AppException If there already exists a user with the same username.
	 */
	public Student(String name, String email, String matricNo, String username, String password, Gender gender,
			Nationality nationality) throws AppException {
		super(username, password);
		this.name = name;
		this.email = email;
		this.matricNo = matricNo;
		this.gender = gender;
		this.nationality = nationality;

		for (Student s : getAllStudents()) {
			if (s.getMatricNo().equals(matricNo)) {
				throw new AppException(
						String.format("A Student with matriculation number %s already exists", matricNo));
			}
		}
	}

	@Override
	public void setAdmin(boolean isAdmin) {
		throw new RuntimeException("Students cannot be administrators");
	}

	/**
	 * Override the base login to only allow the student to login if the current
	 * time is within the configured access period
	 * 
	 * @throws AppException if the student is not allowed to login right now
	 * @return {@code true} if the student has logged in successfully, or
	 *         {@code false} otherwise
	 */
	@Override
	public boolean login(String password) throws AppException {
		if (!super.login(password))
			return false;

		assertCanLogin();
		return true;
	}

	/**
	 * Determine whether a student is allowed to login based on the current time and
	 * access period configured. If the access period is not configured, this
	 * function always returns false.
	 * 
	 * @throws AppException if the student is not allowed to login right now
	 */
	private void assertCanLogin() throws AppException {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime accessPeriodStart = (LocalDateTime) get("student-accessperiod", "start");
		LocalDateTime accessPeriodEnd = (LocalDateTime) get("student-accessperiod", "end");

		if (accessPeriodStart == null || accessPeriodEnd == null) {
			throw new AppException("Access period not set");
		}

		if (now.isBefore(accessPeriodStart)) {
			throw new AppException(String.format("Only allowed to login after %s", accessPeriodStart));
		}

		if (now.isAfter(accessPeriodEnd)) {
			throw new AppException(String.format("Only allowed to login before %s", accessPeriodEnd));
		}
	}

	/**
	 * Set the access period start and end date and times for the student object.
	 * 
	 * @param start The starting date and time of MySTARS access period.
	 * @param end   The ending date and time of MySTARS access period.
	 */
	public static void setAccessPeriod(LocalDateTime start, LocalDateTime end) {
		store("student-accessperiod", "start", start);
		store("student-accessperiod", "end", end);
	}

	/**
	 * Retrieve the period in which students are allowed to login as a string.
	 * 
	 * @return "yyyy-mm-ddThh:mm:ss to yyyy-mm-ddThh:mm:ss" if a access period is
	 *         set, or {@code null} otherwise.
	 */
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
	 * Return the student's email address.
	 * 
	 * @return The student's email.
	 */
	public String getEmail() {
		return email;
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

	/**
	 * Returns a list of all the students.
	 * 
	 * @return The list of all the students.
	 */
	public static ArrayList<Student> getAllStudents() {
		ArrayList<Student> students = new ArrayList<>();

		for (User user : User.getAllUsers()) {
			if (user instanceof Student) {
				students.add((Student) user);
			}
		}
		return students;
	}
}

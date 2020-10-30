package mystars.entities;

import mystars.enums.*;

/**
 * <h1>Class: student</h1>
 * 
 * This student class inherits from the user class.
 * 
 */

public class Student extends User {
	private String name;
	private String matricNo;

	// Create an instance of each enum above per student.
	private Gender gender;
	private Nationality nationality;

	// Create a timetable object for the student object.
	Timetable studentTimetable = new Timetable();

	/**
	 * This class is responsible for the creation of a student object.
	 * 
	 * @param name        The student's name.
	 * @param matricNo    The student's matriculation number.
	 * @param userName    The student's username.
	 * @param password    The student's password.
	 * @param gender      The student's gender.
	 * @param nationality The student's nationality.
	 */
	public Student(String name, String matricNo, String userName, String password, Gender gender,
			Nationality nationality) {
		super(userName, password);
		this.matricNo = matricNo;

		this.gender = gender;
		this.nationality = nationality;
	}

	// This class returns the student's name.
	public String getName() {
		return name;
	}

	// This class returns the student's gender as a category from the Gender enum.
	public Gender getGender() {
		return gender;
	}

	// This class returns the student's timetable object.
	public Timetable getTimetable() {
		return studentTimetable;
	}

	public String getMetricNo() {
		return matricNo;
	}
}

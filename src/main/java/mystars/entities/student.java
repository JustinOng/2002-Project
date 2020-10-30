package mystars.entities;
import user;

/**
 * <h1>Class: student</h1>
 * 
 * This student class inherits from the user class.
 * 
 */

public class student extends user
{
	private String name;
	private String matricNo;
	
	/**
	 * Enum is a special class representing a group of constants
	 * serving as final unchangeable variables.
	 */
	private enum Gender
	{
		Male, Female;
	}
	
	private enum Nationality
	{
		Singaporean;
	}
	
	//Create an instance of each enum above per student.
	private Gender gender;
	private Nationality nationality;
	
	//Create a timetable object for the student object.
	timetable studentTimetable = new timetable();
	
	/**
	 * This class is responsible for the creation of a student object.
	 * 
	 * @param name The student's name.
	 * @param matricNo The student's matriculation number.
	 * @param userName The student's username.
	 * @param password The student's password.
	 * @param gender The student's gender.
	 * @param nationality The student's nationality.
	 */
	public student(String name, String matricNo, String userName, String password, Gender gender, Nationality nationality)
	{
		super(userName, password);
		this.matricNo = matricNo;
		
		this.gender = gender;
		this.nationality = nationality;
	}
	
	//This class returns the student's name.
	public String getName()
	{
		return name;
	}
	
	//This class returns the student's gender as a category from the Gender enum.
	public Gender getGender()
	{
		return gender;
	}
	
	//This class returns the student's timetable object.
	public timetable getTimetable()
	{
		return studentTimetable;
	}
}

package mystars.forms;

import mystars.enums.School;

/**
 * <h1>Class: CreateCourseResponse</h1>
 * 
 * This class manages the form for the creation of courses.
 */
public class CreateCourseResponse {
	/**
	 * Stores the course code.
	 */
	private final String code;
	
	/**
	 * Stores the course name.
	 */
	private final String name;
	
	/**
	 * Stores the school object that the course belongs to.
	 */
	private final School school;
	
	/**
	 * Stores the academic units awarded by the course.
	 */
	private final int au;
	
	/**
	 * Sets the parameters of the course: Code, name, school and academic units.
	 * @param code		The course code.
	 * @param name		The course name.
	 * @param school	The school the course belongs to.
	 * @param au		The academic units awarded by the course.
	 */
	public CreateCourseResponse(String code, String name, School school, int au) {
		this.code = code;
		this.name = name;
		this.school = school;
		this.au = au;
	}
	
	/**
	 * Returns the course code.
	 * 
	 * @return The course code.
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * Returns the course name.
	 * 
	 * @return The course name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the school the course belongs to.
	 * 
	 * @return The school the course belongs to.
	 */
	public School getSchool() {
		return school;
	}
	
	/**
	 * Returns the academic units awarded by the course.
	 * 
	 * @return The academic units awarded by the course.
	 */
	public int getAu() {
		return au;
	}
}

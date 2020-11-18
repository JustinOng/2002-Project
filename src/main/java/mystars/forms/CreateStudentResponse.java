package mystars.forms;

/**
 * <h1>Class: CreateStudentResponse</h1>
 * 
 * This class manages the form for the creation of students.
 */
public class CreateStudentResponse {
	/**
	 * The student's username.
	 */
	private final String username;
	
	/**
	 * The student's password hash.
	 */
	private final String password;
	
	/**
	 * The student's name.
	 */
	private final String name;
	
	/**
	 * The student's matriculation number.
	 */
	private final String matricNo;
	
	/**
	 * The student's gender.
	 */
	private final String gender;
	
	/**
	 * The student's nationality.
	 */
	private final String nationality;

	/**
	 * Sets the parameters for the student.
	 * 
	 * @param username		The student's username.
	 * @param password		The student's password hash.
	 * @param name			The student's name.
	 * @param matricNo		The student's matriculation number.
	 * @param gender		The student's gender.
	 * @param nationality	The student's nationality.
	 */
	public CreateStudentResponse(String username, String password, String name, String matricNo, String gender,
			String nationality) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.matricNo = matricNo;
		this.gender = gender;
		this.nationality = nationality;
	}

	/**
	 * Returns the student's username.
	 * 
	 * @return The student's username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Returns the student's password hash.
	 * 
	 * @return The student's password hash.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Returns the student's name.
	 * 
	 * @return The student's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the student's matriculation number.
	 * 
	 * @return The student's matriculation number.
	 */
	public String getMatricNo() {
		return matricNo;
	}

	/**
	 * Returns the student's gender.
	 * 
	 * @return The student's gender.
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Returns the student's nationality.
	 * 
	 * @return The student's nationality.
	 */
	public String getNationality() {
		return nationality;
	}
}

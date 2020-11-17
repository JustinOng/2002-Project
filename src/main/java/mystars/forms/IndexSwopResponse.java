package mystars.forms;

/**
 * <h1>Class: IndexSwopResponse</h1>
 * 
 * This class manages the form for students to swap their index with another student.
 */
public class IndexSwopResponse {
	/**
	 * Stores the index to be given up for the swop.
	 */
	private final int indexA;
	
	/**
	 * Stores the index to be received from the swop.
	 */
	private final int indexB;
	
	/**
	 * Stores the student's username.
	 */
	private final String username;
	
	/**
	 * Stores the student's password hash.
	 */
	private final String password;
	
	/**
	 * Sets the parameters for the student performing an index swop.
	 * 
	 * @param indexA	The student's index to be given up for the swop.
	 * @param indexB	The student's index to be received from the swop.
	 * @param username	The student's username.
	 * @param password	The student's password hash.
	 */
	public IndexSwopResponse(int indexA, int indexB, String username, String password) {
		this.indexA = indexA;
		this.indexB = indexB;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Returns the student's index to be given up for the swop.
	 * 
	 * @return The student's index to be given up for the swop.
	 */
	public int getIndexA() {
		return indexA;
	}
	
	/**
	 * Returns the student's index to be received from the swop.
	 * 
	 * @return The student's index to be received from the swop.
	 */
	public int getIndexB() {
		return indexB;
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
}

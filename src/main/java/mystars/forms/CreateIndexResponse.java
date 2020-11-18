package mystars.forms;

/**
 * <h1>Class: CreateIndexResponse</h1>
 * 
 * This class manages the form for the creation of course indexes.
 */
public class CreateIndexResponse {
	/**
	 * The course index number.
	 */
	private final int number;
	
	/**
	 * The maximum number of students that can be enrolled into the course index.
	 */
	private final int maxEnrolled;
	
	/**
	 * Sets the course index number and the maximum number of students that can be enrolled.
	 * 
	 * @param number		The course index number.
	 * @param maxEnrolled	The maximum number of students that can be enrolled.
	 */
	public CreateIndexResponse(int number, int maxEnrolled) {
		this.number = number;
		this.maxEnrolled = maxEnrolled;
	}
	
	/**
	 * Returns the course index number.
	 * 
	 * @return The course index number.
	 */
	public int getNumber() {
		return number;
	}
	
	/**
	 * Returns the maximum number of students that can be enrolled.
	 * 
	 * @return The maximum number of students that can be enrolled.
	 */
	public int getMaxEnrolled() {
		return maxEnrolled;
	}
}

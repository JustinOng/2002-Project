package mystars.entities;

/**
 * <h1>Class: admin</h1>
 * 
 * This admin class inherits from the user class.
 */

public class Admin extends User {
	/**
	 * Serialization of the course ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This class is responsible for the creation of an administrator object.
	 * 
	 * @param username 	The administrator's username.
	 * @param password 	The administrator's password.
	 * @param isAdmin 	Boolean variable set to true as objects from this class have admin rights.
	 * @throws UserAlreadyExistsException If there already exists a user with the same username.
	 */
	public Admin(String username, String password) throws Exception	{
		super(username, password);
		isAdmin = true;
	}
}

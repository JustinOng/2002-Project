package mystars.entities;

import mystars.exceptions.AppException;

/**
 * <h1>Class: Admin</h1>
 * 
 * This class manages the creation of administrator objects from user objects.
 * It inherits from the user class.
 */

public class Admin extends User
{
	/**
	 * ID for versioning of serialized data.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This class is responsible for the creation of an administrator object.
	 * 
	 * @param username The administrator's username.
	 * @param password The administrator's password.
	 * @param isAdmin Boolean variable set to true as objects from this class have admin rights.
	 * @throws UserAlreadyExistsException 
	 */
	public Admin(String username, String password) throws AppException
	{
		super(username, password);
		isAdmin = true;
	}
}

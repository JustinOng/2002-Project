package mystars.entities;

import mystars.exceptions.*;

/**
 * <h1>Class: admin</h1>
 * 
 * This admin class inherits from the user class.
 */

public class Admin extends User
{
	/**
	 * This class is responsible for the creation of an administrator object.
	 * 
	 * @param username The administrator's username.
	 * @param password The administrator's password.
	 * @param isAdmin Boolean variable set to true as objects from this class have admin rights.
	 * @throws UserAlreadyExistsException 
	 */
	public Admin(String username, String password) throws UserAlreadyExistsException
	{
		super(username, password);
		isAdmin = true;
	}
}

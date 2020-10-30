package mystars.entities;
import user;

/**
 * <h1>Class: admin</h1>
 * 
 * This admin class inherits from the user class.
 */

public class admin extends user
{
	/**
	 * This class is responsible for the creation of an administrator object.
	 * 
	 * @param userName The administrator's username.
	 * @param password The administrator's password.
	 * @param isAdmin Boolean variable set to true as objects from this class have admin rights.
	 */
	public admin(String userName, String password)
	{
		super(userName, password);
		isAdmin = true;
	}
}

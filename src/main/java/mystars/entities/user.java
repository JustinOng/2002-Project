package mystars.entities;
/**
 * <h1>Class: user</h1>
 * 
 * This user class serves as the superclass for 'admin' and 'student'.
 * It manages the verification of a user's password when logging in.
 * 
 * @param userName The user name assigned to the admin or student user
 * instantiated by the subclasses.
 * 
 * @param passwordHash The hash of the password for an instance of the user
 * instantiated by the subclasses.
 * 
 * @param isAdmin Boolean variable used by the subclasses to determine if a user
 * is an admin with admin rights, or only a student with no admin rights.
 * 
 * @param users A hashmap hash table containing mapped key value pairs of user
 * names and their password hashes.  
 */

import java.util.HashMap;

public class user
{
	protected String userName;
	private String passwordHash;
	protected boolean isAdmin = false;
	
	//Hashmap tables contain mapped key pair values.
	HashMap<String, user> users = new HashMap<String, user>();
	
	/**
	 * This method
	 * 
	 * Method is accessible to objects of this class and objects of all subclasses.
	 * 
	 * @param userName The user's username.
	 * @param password Theuser's password. 
	 */
	protected user(String userName, String password)
	{
		
	}
	
	/**
	 * This method calculates the hash of the user's password.
	 * 
	 * @param input The user's plain text password.
	 * @return
	 */
	protected String hashPassword(String input)
	{
		
	}
	
	/**
	 * This method
	 * 
	 * @param userName The user's username.
	 * @param password Theuser's password. 
	 */
	public static user login(String userName, String password)
	{
		
	}
	
	/**
	 * This method
	 * 
	 * @param password The user's password.
	 */
	public user login(String password)
	{
		
	}
}

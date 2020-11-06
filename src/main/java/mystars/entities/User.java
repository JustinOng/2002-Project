package mystars.entities;

/**
 * <h1>Class: user</h1>
 * 
 * This user class serves as the superclass for 'admin' and 'student'.
 * It manages the verification of a user's password when logging in.
 * 
 * @param username The user name assigned to the admin or student user
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

import java.util.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.HashMap;

public abstract class User {
	protected String username;
	private byte[] passwordHash;
	protected boolean isAdmin = false;

	// Hashmap tables contain mapped key pair values.
	private static HashMap<String, User> users = new HashMap<String, User>();

	/**
	 * This method
	 * 
	 * Method is accessible to objects of this class and objects of all subclasses.
	 * 
	 * @param userName The user's username.
	 * @param password Theuser's password.
	 * @throws UserAlreadyExistsException 
	 */
	protected User(String username, String password) throws Exception {
		if (users.containsKey(username)) {
			throw new Exception(String.format("%s already exists", username));
		}
		
		this.username = username;
		this.passwordHash = hashString(password);

		users.put(username, this);
	}

	/*
	 * ------------------------------------------------------ Additional functions
	 * for calculating password hash.
	 */

	private static byte[] hashString(String input) {
		// Static getInstance method is called with hashing SHA
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// convert to unchecked exception (since if it happens, treat it as fatal)
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		// digest() method called
		// to calculate message digest of an input
		// and return array of byte
		return md.digest(input.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * This method
	 * 
	 * @param userName The user's username.
	 * @param password Theuser's password.
	 * @throws InvalidLoginException
	 */
	public static User login(String username, String password) throws Exception {
		if (!users.containsKey(username)) {
			throw new Exception("Invalid login");
		}

		User user = users.get(username);
		if (!user.login(password)) {
			throw new Exception("Invalid login");
		}

		return user;
	}

	/**
	 * This method
	 * 
	 * @param password The user's password.
	 */
	public boolean login(String password) {
		return Arrays.equals(hashString(password), this.passwordHash);
	}
}

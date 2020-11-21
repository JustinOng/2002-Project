package mystars.entities;

import java.util.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import mystars.exceptions.AppException;

/**
 * <h1>Class: User</h1>
 * 
 * This user class serves as the superclass for 'admin' and 'student'. It
 * manages the verification of a user's password when logging in.
 */

public abstract class User extends Entity {
	/**
	 * ID for versioning of serialized data.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The user name assigned to the admin or student user.
	 */
	protected String username;

	/**
	 * Array to store the hash of the user's password. 'byte' is a primitive data
	 * type 8 bits long.
	 */
	private byte[] passwordHash;

	/**
	 * Boolean variable to determine if a user has admin rights. Students do not
	 * have admin rights. Administrators have admin rights.
	 */
	protected boolean isAdmin = false;

	/**
	 * Create a new user after ensuring that there is no other user with the same
	 * username.
	 * 
	 * @param username The user's username.
	 * @param password The user's password.
	 * @throws AppException If there already exists a user with the same username.
	 */
	protected User(String username, String password) throws AppException {
		if (get("user", username) != null) {
			throw new AppException(String.format("%s already exists", username));
		}
		this.username = username;
		this.passwordHash = hashString(password);

		store("user", username, this);
	}

	/**
	 * Calculates the SHA-256 hash for an input string.
	 * 
	 * @param input The user's password.
	 * @return The SHA-256 hash of the user's password.
	 */
	private static byte[] hashString(String input) {
		// Static getInstance method is called with hashing SHA.
		MessageDigest md;

		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// Convert to unchecked AppException (since if it happens, treat it as fatal).
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		// Digest() method called to calculate message digest of an input and return
		// array of byte.
		return md.digest(input.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * Validates a user's login by verifying that their username and password
	 * matches the entry in the database.
	 * 
	 * @param username The user's username.
	 * @param password The user's password.
	 * @throws AppException If the login is unsuccessful due to incorrect username
	 *                      or password.
	 * @return The user object of the user successfully logging in.
	 */
	public static User login(String username, String password) throws AppException {
		if (get("user", username) == null) {
			throw new AppException("Invalid login");
		}

		User user = (User) get("user", username);
		if (!user.login(password)) {
			throw new AppException("Invalid login");
		}
		return user;
	}

	/**
	 * Compare the hash of the user's entered password with the password hash stored
	 * for that user in the database.
	 * 
	 * @param password The user's password.
	 * @return Boolean value indicating if the hash of the entered password matches
	 *         the stored hash.
	 * @throws AppException If the login failed
	 */
	public boolean login(String password) throws AppException {
		return Arrays.equals(hashString(password), this.passwordHash);
	}

	/**
	 * Retrieves all users
	 * 
	 * @return list of all users
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<User> getAllUsers() {
		return (ArrayList<User>) (ArrayList<?>) getAll("user");
	}
}

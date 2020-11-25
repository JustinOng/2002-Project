package mystars.forms;

/**
 * This class manages the form for the user's login.
 */
public class LoginResponse {
	/**
	 * The user's username.
	 */
	private final String username;

	/**
	 * The user's password hash.
	 */
	private final String password;

	/**
	 * Sets the user's username and password hash.
	 * 
	 * @param username The user's username.
	 * @param password The user's password hash.
	 */
	public LoginResponse(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Returns the user's username.
	 * 
	 * @return The user's username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Returns the user's password hash.
	 * 
	 * @return The user's password hash.
	 */
	public String getPassword() {
		return password;
	}
}

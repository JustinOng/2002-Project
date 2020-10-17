package mystars.forms;

public class LoginResponse {
	private final String username;
	private final String password;
	private final String loginType;

	public LoginResponse(String username, String password, String loginType) {
		this.username = username;
		this.password = password;
		this.loginType = loginType;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getLoginType() {
		return loginType;
	}
}

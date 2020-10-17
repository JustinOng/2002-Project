package mystars.forms;

public class CreateStudentResponse {
	private final String username;
	private final String password;
	private final String name;
	private final String matricNo;
	private final String major;
	private final String nationality;

	public CreateStudentResponse(String username, String password, String name, String matricNo, String major,
			String nationality) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.matricNo = matricNo;
		this.major = major;
		this.nationality = nationality;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getMatricNo() {
		return matricNo;
	}

	public String getMajor() {
		return major;
	}

	public String getNationality() {
		return nationality;
	}
}

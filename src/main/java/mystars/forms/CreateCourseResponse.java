package mystars.forms;

import mystars.School;

public class CreateCourseResponse {
	private final String code;
	private final String name;
	private final School school;
	
	public CreateCourseResponse(String code, String name, School school) {
		this.code = code;
		this.name = name;
		this.school = school;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
	public School getSchool() {
		return school;
	}
}

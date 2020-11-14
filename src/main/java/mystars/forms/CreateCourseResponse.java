package mystars.forms;

import mystars.enums.School;

public class CreateCourseResponse {
	private final String code;
	private final String name;
	private final School school;
	private final int au;
	
	public CreateCourseResponse(String code, String name, School school, int au) {
		this.code = code;
		this.name = name;
		this.school = school;
		this.au = au;
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
	
	public int getAu() {
		return au;
	}
}

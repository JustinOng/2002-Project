package mystars.forms;

public class CreateLessonResponse {
	private final String lessonType;
	private final String day;
	private final String location;
	private final String groupNo;
	private final boolean[] weeks;
	private final int startPeriod;
	private final int endPeriod;

	public CreateLessonResponse(String lessonType, String day, String location, String groupNo, boolean[] weeks,
			int startPeriod, int endPeriod) {
		this.lessonType = lessonType;
		this.day = day;
		this.location = location;
		this.groupNo = groupNo;
		this.weeks = weeks;
		this.startPeriod = startPeriod;
		this.endPeriod = endPeriod;
	}

	public String getLessonType() {
		return lessonType;
	}
	
	public String getDay() {
		return day;
	}
	
	public String getLocation() {
		return location;
	}
	
	public String getGroupNo() {
		return groupNo;
	}
	
	public boolean[] getWeeks() {
		return weeks;
	}
	
	public int getStartPeriod() {
		return startPeriod;
	}
	
	public int getEndPeriod() {
		return endPeriod;
	}
}

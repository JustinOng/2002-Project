
public class Lesson {

	private Index index;
	private int LessonType;
	private String day;
	private String location;
	private String groupNo;
	private boolean[] week;
	private int period;

	
	public Lesson(Index index, int LessonType, String day, String groupNo, String location, boolean[] week, int period) {
		this.index = index;
		this.LessonType = LessonType;
		this.day = day;
		this.location = location;
		this.groupNo = groupNo;
		this.week = new boolean[13];
		this.period = period;
	}
}

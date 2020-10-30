package mystars.entities;

import mystars.enums.*;

public class Lesson {

	private Index index;
	private LessonType lessonType;
	private Day day;
	private String location;
	private String groupNo;
	private boolean[] week;
	private int period;

	public Lesson(Index index, LessonType lessonType, Day day, String groupNo, String location, boolean[] week,
			int period) {
		this.index = index;
		this.lessonType = lessonType;
		this.day = day;
		this.location = location;
		this.groupNo = groupNo;
		this.week = week;
		this.period = period;
	}
}

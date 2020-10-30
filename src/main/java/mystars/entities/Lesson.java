package mystars.entities;

import mystars.enums.*;

public class Lesson {

	private Index index;
	private LessonType lessonType;
	private Day day;
	private String location;
	private String groupNo;
	private boolean[] weeks;
	private int period;

	public Lesson(Index index, LessonType lessonType, Day day, String groupNo, String location, boolean[] weeks,
			int period) {
		this.index = index;
		this.lessonType = lessonType;
		this.day = day;
		this.location = location;
		this.groupNo = groupNo;
		this.weeks = weeks;
		this.period = period;
	}

	public boolean clashesWith(Lesson l) {
		for (int week = 0; week < weeks.length; week++) {
			if (this.weeks[week] && l.weeks[week]) {
				// if both lessons occur on the same week
				if (this.day == l.day && this.period == l.period) {
					return true;
				}
			}
		}
		
		return false;
	}
}

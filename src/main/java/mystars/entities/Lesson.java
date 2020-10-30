package mystars.entities;

import mystars.enums.*;

public class Lesson {

	private Index index;
	private LessonType lessonType;
	private Day day;
	private String location;
	private String groupNo;
	private boolean[] weeks;
	private int startPeriod;
	private int endPeriod;

	public Lesson(Index index, LessonType lessonType, Day day, String groupNo, String location, boolean[] weeks,
			int startPeriod, int endPeriod) {
		this.index = index;
		this.lessonType = lessonType;
		this.day = day;
		this.location = location;
		this.groupNo = groupNo;
		this.weeks = weeks;
		this.startPeriod = startPeriod;
		this.endPeriod = endPeriod;
	}

	public boolean clashesWith(Lesson l) {
		for (int week = 0; week < weeks.length; week++) {
			if (this.weeks[week] && l.weeks[week]) {
				// if both lessons occur on the same week
				if (this.day == l.day) {
					if (
							(this.startPeriod >= l.startPeriod && this.startPeriod <= l.endPeriod) ||
							(l.startPeriod >= this.startPeriod && l.startPeriod <= this.endPeriod)
					) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
}
